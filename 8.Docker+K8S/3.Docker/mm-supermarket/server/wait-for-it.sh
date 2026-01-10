#!/bin/bash
# wait-for-it.sh
# 等待特定主机和端口可用

host="$1"
port="$2"
timeout="${3:-30}" # 默认等待30秒

echo "等待 $host:$port 可用..."

while ! nc -z "$host" "$port"; do
  sleep 1
  timeout=$((timeout - 1))
  if [ "$timeout" -le 0 ]; then
    echo "超时，无法连接到 $host:$port"
    exit 1
  fi
done

echo "$host:$port 可用"