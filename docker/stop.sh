#!/bin/bash

echo "Stopping Project Cloud services..."

# 停止后端服务
pkill -f 'cloud-gateway-1.0.0.jar'
pkill -f 'cloud-auth-1.0.0.jar'
pkill -f 'cloud-system-1.0.0.jar'
pkill -f 'cloud-generator-1.0.0.jar'
pkill -f 'cloud-file-1.0.0.jar'

# 停止基础设施
docker-compose down

echo "All services stopped!"
