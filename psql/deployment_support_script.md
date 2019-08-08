## Startup steps (how to):
Log into your local machine, if you want to deploy in your Amazon EC2 Instance please follow below commands:
```
1. Amazon linux 2 AMI (optional for local machine deployment)
ssh -i ~/.ssh/pem/you_pem_key ec2-user@ec2_pub_ip

2. Install docker--
sudo -s
yum install -y docker
usermod -a -G docker ec2-user

3. Enable docker auto start on boot--
systemctl enable docker
systemctl start docker
systemctl status docker

4. Download your source code--
sudo yum install -y git
git clone https://github.com/sumitJRVS/Trading_app.git

5. Build the trading_app docker image--
sudo -s
cd trading_app
docker build -t trading-app .
docker image ls

6. Build the psql docker container--2
cd psql/
sudo docker build -t jrvs-psql .
docker container ls

7. Check/log into psql connection--
psql -h localhost -U postgres -W
\q

8. Initiate Dattabase and Schema
cd trading_app/sql_dll
psql -h localhost -U postgres -f init_db.sql
psql -h localhost -U postgres -f schema.sql

(init_db.sql --> CREATE DATABASE jrvstrading_test;
GRANT ALL PRIVILEGES ON DATABASE jrvstrading_test TO postgres;)

9. To run springboot app using docker--

eg. for locahmachine--
sudo docker run --rm --name jrvs-psql --restart unless-stopped -e
POSTGRES_PASSWORD=password -e POSTGRES_DB=jrvstrading -e
POSTGRES_USER=postgres --network trading-net -d -p 5432:5432 jrvs-psql

```
Once you done with the above steps; you just need to follow below script for 2nd time usage of this app.
```
1. Strat docker and pull docker image
systemctl start docker--
docker pull postgres
docker volume create pgdata

2. Run docker image--
sudo docker run --rm --name jrvs-psql --restart unless-stopped -e
POSTGRES_PASSWORD=password -e POSTGRES_DB=jrvstrading -e
POSTGRES_USER=postgres --network trading-net -d -p 5432:5432 jrvs-psql
```
Misc. commands and troubleshooting:
```
1. Stopping PostgreSQL--
docker stop lil-postgres

2. Stopping Docker--
systemctl stop dcoker

3. Removing unused docker container/ images--
docker image prune
docker container prune
```
Loginto swagger browser using
`localhost:8080/swagger-ui.html`
