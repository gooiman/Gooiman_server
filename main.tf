provider "aws" {
  region = "ap-northeast-2"
}

# VPC 설정
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}

# 서브넷 생성
resource "aws_subnet" "main" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "ap-northeast-2a"
}

# 인터넷 게이트웨이 생성
resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id
}

# 라우팅 테이블 생성 및 인터넷 트래픽 라우팅
resource "aws_route_table" "main" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }
}

# 서브넷을 라우팅 테이블에 연결
resource "aws_route_table_association" "main" {
  subnet_id      = aws_subnet.main.id
  route_table_id = aws_route_table.main.id
}

# EC2 인스턴스 프로필
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = aws_iam_role.ec2_role.name
}

# EC2 IAM 역할
resource "aws_iam_role" "ec2_role" {
  name = "ec2_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

# ECR 접근 정책
resource "aws_iam_role_policy" "ecr_access_policy" {
  name = "ecr_access_policy"
  role = aws_iam_role.ec2_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ecr:GetAuthorizationToken",
          "ecr:BatchCheckLayerAvailability",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage"
        ]
        Resource = "*"
      }
    ]
  })
}

# S3 접근 정책
resource "aws_iam_role_policy" "s3_access_policy" {
  name = "s3_access_policy"
  role = aws_iam_role.ec2_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:ListBucket"
        ]
        Resource = [
          "${aws_s3_bucket.codedeploy.arn}",
          "${aws_s3_bucket.codedeploy.arn}/*"
        ]
      }
    ]
  })
}

# EC2 인스턴스 보안 그룹
resource "aws_security_group" "ec2_sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# EC2 인스턴스
resource "aws_instance" "app" {
  ami = "ami-0c55b159cbfafe1f0"  # Amazon Linux 2 AMI
  instance_type        = "t2.micro"
  subnet_id            = aws_subnet.main.id
  security_groups = [aws_security_group.ec2_sg.name]
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

  user_data = <<-EOF
    #!/bin/bash
    yum update -y
    yum install -y docker
    service docker start
    usermod -a -G docker ec2-user

    # Docker Compose 설치
    sudo curl -L "https://github.com/docker/compose/releases/download/2.29.7/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # CodeDeploy 에이전트 설치
    yum install -y ruby wget
    cd /home/ec2-user
    wget https://aws-codedeploy-${var.region}.s3.${var.region}.amazonaws.com/latest/install
    chmod +x ./install
    ./install auto
  EOF
}

# RDS 보안 그룹
resource "aws_security_group" "rds_sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = 3306
    to_port   = 3306
    protocol  = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# RDS 인스턴스 생성
resource "aws_db_instance" "db" {
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8"
  instance_class       = "db.t2.micro"
  db_name              = "gooiman"
  username             = var.database_user
  password             = var.database_password
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name = aws_db_subnet_group.db_subnet.id
}

# DB 서브넷 그룹
resource "aws_db_subnet_group" "db_subnet" {
  name = "db_subnet_group"
  subnet_ids = [aws_subnet.main.id]
}

# ECR 리포지토리 생성
resource "aws_ecr_repository" "app" {
  name = "gooiman"
}

# S3 버킷 생성
resource "aws_s3_bucket" "codedeploy" {
  bucket = "gooiman-deploy-bucket"
}

# CodeDeploy 애플리케이션
resource "aws_codedeploy_app" "app" {
  name = "GooimanDeploy"
}

# CodeDeploy 배포 그룹
resource "aws_codedeploy_deployment_group" "deployment" {
  app_name              = aws_codedeploy_app.app.name
  deployment_group_name = "GooimanDeploymentGroup"
  service_role_arn      = aws_iam_role.codedeploy_role.arn
  ec2_tag_filter {
    key   = "Name"
    type  = "KEY_AND_VALUE"
    value = "MyAppInstance"
  }
  deployment_config_name = "CodeDeployDefault.OneAtATime"
  auto_rollback_configuration {
    enabled = true
    events = ["DEPLOYMENT_FAILURE"]
  }
}

# IAM 역할 - CodeDeploy를 위한 역할
resource "aws_iam_role" "codedeploy_role" {
  name = "CodeDeployRole"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "codedeploy.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

# IAM 역할 정책 연결
resource "aws_iam_role_policy_attachment" "codedeploy_role_policy" {
  role       = aws_iam_role.codedeploy_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole"
}