#!/bin/bash

echo "Stopping Project Cloud services..."

# 停止所有服务
docker-compose down

echo "All services stopped!"
