#!/bin/bash
set -e

echo "=== SLAVE INIT STARTED ==="

# Ждем пока мастер будет полностью готов
echo "Waiting for master to be ready..."
until pg_isready -h postgres-master -U hl1 -d postgres; do
  echo "Master not ready yet, waiting..."
  sleep 5
done

# Дополнительная пауза чтобы мастер успел применить все настройки
echo "Master is ready, waiting for configuration..."
sleep 10

# Останавливаем PostgreSQL если запущен
echo "Stopping PostgreSQL for setup..."
pg_ctl -D /var/lib/postgresql/data -m fast -w stop || true

# Очищаем данные (только если папка не пустая)
if [ "$(ls -A /var/lib/postgresql/data)" ]; then
  echo "Cleaning existing data directory..."
  rm -rf /var/lib/postgresql/data/*
fi

# Создаем реплику с мастера
echo "Creating replica from master..."
PGPASSWORD=replicator123 pg_basebackup -h postgres-master -p 5432 -D /var/lib/postgresql/data -U replicator -P -R -X stream
#PGPASSWORD=replicator123 pg_basebackup -h postgres-master -p 5432 -D /var/lib/postgresql/data -U replicator -P -X stream

# Set hostnames
echo "Configuring application_name... $HOSTNAME"
echo "primary_conninfo = 'host=postgres-master port=5432 user=replicator password=replicator123 application_name=$HOSTNAME'" >> /var/lib/postgresql/data/postgresql.auto.conf

# Создаем файл для standby режима
echo "Configuring standby mode..."
touch /var/lib/postgresql/data/standby.signal

echo "=== SLAVE INIT COMPLETED ==="
