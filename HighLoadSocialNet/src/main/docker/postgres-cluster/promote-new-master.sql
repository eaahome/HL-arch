select pg_promote();

alter system set synchronous_standby_names='ANY 1 ("postgres-slave2")';
select pg_reload_conf();
