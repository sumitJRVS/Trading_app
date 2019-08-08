## Execute below command to make image and create a network bridge ;

docker build -t jrvs-psql .
docker network create --driver bridge trading-net

## Attach container to the network bridge and docker image and then run it;

docker run --rm --name jrvs-psql
-e POSTGRES_PASSWORD=password
-e POSTGRES_DB=jrvstrading
-e POSTGRES_USER=postgres
--network trading-net
-d -p 5432:5432 jrvs-psql
