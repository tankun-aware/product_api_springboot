#!/bin/sh

export PGPASSWORD='Srg9PUT8gGiLU6365ZHlMyhnzlxZoOEE'

pg_dump -h dpg-crucbkjtq21c738gkic0-a.singapore-postgres.render.com \
    -U mydb_851u_user \
    -d mydb_851u \
    --table=users \
    --data-only \
    -f /dump/users_data.sql

psql -h localhost -U testuser -d test_name -f /dump/users_data.sql
