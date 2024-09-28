variable "environment" {
  description = "Deployment environment"
  type        = string
}

variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "ap-northeast-2"
}

variable "database_user" {
  description = "Database username"
  type = string
  default = "admin"
}

variable "database_password" {
    description = "Database password"
    type = string
    default = "password"
}