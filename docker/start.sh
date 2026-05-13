#!/bin/bash

echo "Starting Project Cloud services..."

# 启动基础设施
docker-compose up -d mysql redis nacos minio

echo "Waiting for services to be ready..."
sleep 30

# 启动后端服务
echo "Starting backend services..."
cd ..
mvn clean package -DskipTests

nohup java -jar cloud-gateway/target/cloud-gateway-1.0.0.jar > logs/gateway.log 2>&1 &
nohup java -jar cloud-auth/target/cloud-auth-1.0.0.jar > logs/auth.log 2>&1 &
nohup java -jar cloud-system/target/cloud-system-1.0.0.jar > logs/system.log 2>&1 &
nohup java -jar cloud-generator/target/cloud-generator-1.0.0.jar > logs/generator.log 2>&1 &
nohup java -jar cloud-file/target/cloud-file-1.0.0.jar > logs/file.log 2>&1 &

echo "All services started!"
