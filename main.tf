provider "aws" {
  region = var.aws_region
}

terraform {
  cloud {
    organization = "gooiman"

    workspaces {
      tags = ["gooiman"]
    }
  }
}

# VPC 설정
resource "aws_vpc" "gooiman" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "gooiman_${var.environment}"
  }
}

# 인터넷 게이트웨이 생성
resource "aws_internet_gateway" "gooiman" {
  vpc_id = aws_vpc.gooiman.id
  tags = {
    Name = "gooiman_igw_${var.environment}"
  }
}

# 퍼블릭 서브넷 생성
resource "aws_subnet" "gooiman_public" {
  vpc_id            = aws_vpc.gooiman.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "gooiman_subnet_public_${var.environment}"
  }
}
# 프라이빗 서브넷 생성 1
resource "aws_subnet" "gooiman_private_1" {
  vpc_id            = aws_vpc.gooiman.id
  cidr_block        = "10.0.101.0/24"
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "gooiman_subnet_private_1_${var.environment}"
  }
}
# 프라이빗 서브넷 생성 2
resource "aws_subnet" "gooiman_private_2" {
  vpc_id            = aws_vpc.gooiman.id
  cidr_block        = "10.0.102.0/24"
  availability_zone = "ap-northeast-2b"
  tags = {
    Name = "gooiman_subnet_private_2_${var.environment}"
  }
}

# 퍼블릭 서브넷 라우팅 테이블 생성 및 인터넷 트래픽 라우팅
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.gooiman.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gooiman.id
  }
}

# 퍼블릭 서브넷을 라우팅 테이블에 연결
resource "aws_route_table_association" "public" {
  subnet_id      = aws_subnet.gooiman_public.id
  route_table_id = aws_route_table.public.id
}

# 프라이빗 서브넷 라우팅 테이블 생성
resource "aws_route_table" "private" {
  vpc_id = aws_vpc.gooiman.id
}

# 프라이빗 서브넷을 라우팅 테이블에 연결
resource "aws_route_table_association" "private" {
  subnet_id      = aws_subnet.gooiman_private_1.id
  route_table_id = aws_route_table.private.id
}

# RDS 보안 그룹
resource "aws_security_group" "rds_sg" {
  vpc_id = aws_vpc.gooiman.id

  ingress {
    from_port = 3306
    to_port   = 3306
    protocol  = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
    security_groups = [aws_security_group.ec2_sg.id]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# DB 서브넷 그룹
resource "aws_db_subnet_group" "db_subnet" {
  name = "db_subnet_group"
  subnet_ids = [aws_subnet.gooiman_private_1.id, aws_subnet.gooiman_private_2.id]
}

# RDS 인스턴스 생성
resource "aws_db_instance" "db" {
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t4g.micro"
  db_name              = "gooiman"
  username             = var.database_user
  password             = var.database_password
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name = aws_db_subnet_group.db_subnet.id
  skip_final_snapshot  = true
  tags = {
    Name = "gooiman_db_${var.environment}"
  }
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
          aws_s3_bucket.codedeploy_bucket.arn,
          "${aws_s3_bucket.codedeploy_bucket.arn}/*"
        ]
      }
    ]
  })
}

# EC2 인스턴스 보안 그룹
resource "aws_security_group" "ec2_sg" {
  vpc_id = aws_vpc.gooiman.id

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  // 개발 환경 8080 포트 허용
  dynamic "ingress" {
    for_each = (var.environment == "dev") ? [1] : []
    content {
      from_port = 8080
      to_port   = 8080
      protocol  = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  // 운영 환경 8080 리다이렉트
  dynamic "ingress" {
    for_each = (var.environment == "prod") ? [1] : []
    content {
      from_port = 8080
      to_port   = 443
      protocol  = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# EC2 인스턴스
resource "aws_instance" "gooiman_api" {
  depends_on = [aws_db_instance.db]

  ami = "ami-06f73fc34ddfd65c2"  # Amazon Linux 2023 AMI
  instance_type        = "t2.micro"
  subnet_id            = aws_subnet.gooiman_public.id
  vpc_security_group_ids = [aws_security_group.ec2_sg.id]
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

  user_data = <<-EOF
    #!/bin/bash
    exec > >(tee /var/log/user-data.log|logger -t user-data -s 2>/dev/console) 2>&1
    yum update -y
    yum install -y docker
    service docker start
    usermod -a -G docker ec2-user

    # Docker Compose 설치
    sudo curl -L "https://github.com/docker/compose/releases/download/v2.29.7/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # CodeDeploy 에이전트 설치
    yum install -y ruby wget
    cd /home/ec2-user
    wget https://aws-codedeploy-${var.aws_region}.s3.${var.aws_region}.amazonaws.com/latest/install
    chmod +x ./install
    ./install auto

    # RDS 주소 host 연결
    sudo touch /etc/profile.d/database.sh
    sudo echo "#!/bin/bash" >> /etc/profile.d/database.sh
    sudo echo "export DATABASE_ADDRESS=${aws_db_instance.db.address}" >> /etc/profile.d/database.sh
    sudo echo "export DATABASE_USERNAME=${var.database_user}" >> /etc/profile.d/database.sh
    sudo echo "export DATABASE_PASSWORD=${var.database_password}" >> /etc/profile.d/database.sh
    source /etc/profile
  EOF
  tags = {
    Name = "gooiman_api_${var.environment}"
  }
}

# ECR 리포지토리 생성
resource "aws_ecr_repository" "gooiman_ecr" {
  name = "gooiman_${var.environment}"

  tags = {
    Name = "gooiman_${var.environment}"
  }
}

# S3 버킷 생성
resource "aws_s3_bucket" "codedeploy_bucket" {
  bucket = "gooiman-${var.environment}-deploy-bucket"
}

# CodeDeploy 애플리케이션
resource "aws_codedeploy_app" "codedeploy" {
  name = "gooiman_${var.environment}_deploy"
}

# CodeDeploy 배포 그룹
resource "aws_codedeploy_deployment_group" "deployment" {
  app_name              = aws_codedeploy_app.codedeploy.name
  deployment_group_name = "gooiman_${var.environment}_deploy_group"
  service_role_arn      = aws_iam_role.codedeploy_role.arn
  ec2_tag_filter {
    type  = "KEY_AND_VALUE"
    key   = "Name"
    value = "gooiman_api_${var.environment}"
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

# RDS 엔드포인트 출력
output "rds_endpoint" {
  value = aws_db_instance.db.address
}