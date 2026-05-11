#!/bin/bash

echo "Starting Project Cloud services..."

# 启动基础服务
echo "Starting MySQL, Redis, Nacos, MinIO..."
docker-compose up -d mysql redis nacos minio

# 等待 MySQL 启动
echo "Waiting for MySQL to start..."
sleep 30

# 等待 Nacos 启动
echo "Waiting for Nacos to start..."
sleep 20

echo "All base services started!"
echo ""
echo "Service URLs:"
echo "  MySQL:     127.0.0.1:3306"
echo "  Redis:     127.0.0.1:6379"
echo "  Nacos:     http://127.0.0.1:8848/nacos"
echo "  MinIO:     http://127.0.0.1:9001"
echo ""
echo "Default credentials:"
echo "  MySQL:     root / root123"
echo "  Redis:     password: redis123"
echo "  Nacos:     nacos / nacos"
echo "  MinIO:     minioadmin / minioadmin"
