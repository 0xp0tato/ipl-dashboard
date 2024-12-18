@echo off
setlocal

REM Variables
set DB_NAME=ipldb
set CONTAINER_NAME=ipl-db
set PG_USER=admin

REM Connect to the PostgreSQL database inside the Docker container and drop all tables
docker exec -it %CONTAINER_NAME% psql -U %PG_USER% -d %DB_NAME% -c "DO $$ DECLARE r RECORD; BEGIN FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE'; END LOOP; END $$;"

echo All tables in the database %DB_NAME% have been dropped.
endlocal
