#!/bin/bash
set -e

echo "🚀🚀🚀 setup-master.sh STARTED - you should see this in logs! 🚀🚀🚀"
#sleep 10  # Дает время подключиться и посмотреть что происходит
echo "Continuing after sleep..."


# Ждем запуска PostgreSQL
until pg_isready -U hl1; do
    sleep 2
done

# Создаем пользователя репликации
psql -U hl1 -c "CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'replicator123';"

# Настройки репликации
psql -U hl1 -c "ALTER SYSTEM SET wal_level = 'replica';"
psql -U hl1 -c "ALTER SYSTEM SET max_wal_senders = 10;"
psql -U hl1 -c "ALTER SYSTEM SET max_replication_slots = 10;"
psql -U hl1 -c "ALTER SYSTEM SET hot_standby = on;"

# pg_hba.conf - добавляем записи для репликации
echo "host replication replicator all md5" >> /var/lib/postgresql/data/pg_hba.conf

# Перезагружаем конфигурацию
psql -U hl1 -c "SELECT pg_reload_conf();"

# Создаем слоты репликации
psql -U hl1 -c "SELECT pg_create_physical_replication_slot('replication_slot_slave1');"
psql -U hl1 -c "SELECT pg_create_physical_replication_slot('replication_slot_slave2');"

echo "Master configuration completed"
