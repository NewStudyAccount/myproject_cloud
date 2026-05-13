#!/bin/bash

echo "Restarting Project Cloud services..."

./stop.sh
sleep 5
./start.sh

echo "All services restarted!"
