#!/bin/bash

export PGPASSWORD='Srg9PUT8gGiLU6365ZHlMyhnzlxZoOEE'

tables=("users" "user_roles" "roles" "products")

for table in "${tables[@]}"; do
    pg_dump -h dpg-crucbkjtq21c738gkic0-a.singapore-postgres.render.com \
        -U mydb_851u_user \
        -d mydb_851u \
        --table="$table" \
        --data-only \
        -f "/dump/${table}_data.sql"
    
done

for table in "${tables[@]}"; do
    psql -h localhost -U testuser -d test_name -f "/dump/${table}_data.sql"
done

psql -h localhost -U testuser -d test_name -c "SELECT * FROM users;"