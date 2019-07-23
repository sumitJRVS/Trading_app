#!/bin/bash
set -e
cd "$(dirname "$0")"

ls lib/trading-1.0-SNAPSHOT.jar

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

export SPRING_PROFILES_ACTIVE=$1
export PSQL_HOST=$2
export PSQL_USER=$3
export PSQL_PASSWORD=$4
export IEX_PUB_TOKEN=$5
export PGPASSWORD=$PSQL_PASSWORD
export PSQL_URL="jdbc:postgresql://${PSQL_HOST}:5432/jrvstrading"

#start docker
systemctl status docker || systemctl start docker || sleep 5

#create docker volume to persist db data
docker volume ls | grep "pgdata" || docker volume create pgdata || sleep 1

#stop existing jrvs-psql container
docker ps | grep jrvs-psql && docker stop $(docker ps | grep jrvs-psql | awk '{print $1}')

#start docker
docker run --rm --name jrvs-psql -e POSTGRES_PASSWORD=$PSQL_PASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 $PSQL_USER
sleep 5

psql -h $PSQL_HOST -U $PSQL_USER -f ./sql_ddl/init_db.sql
psql -h $PSQL_HOST -U $PSQL_USER -d jrvstrading -f ./sql_ddl/schema.sql



sleep 5

#verify
psql -h localhost -U postgres -d jrvstrading
echo To verify the databse and table created';' in database-shell-prompt just type '\dt;'
#run springboot app




