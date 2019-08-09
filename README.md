
## Trading REST API

## Introduction:

   - This trading REST API SpingBoot Java app is built using micro-services. The SpingBoot handles internal dependencies for the app here. The app is used to find store stock prices from live/offline market and do trade within account and also buy buy stocks.
    - The user can save its stock data with the account and obtain later market quote in future for its portfolio. The account has some basic features to deposit-withdraw funds, create-delete account functions.
    - The data is obtained by IEX plateform and user need to create an account on the webpage[https://iextrading.com/] and obtain the key to paste it in the environment variable within the app.
    - To consume Trading REST API we created, use Postman or Swagger v2 webpage(port 80, can be accessible by Http request-webpage-browser/cell)
    -  Ultimately Frontend developers will be able to consume these API via UI efficiently. API specifications using `http://localhost:8080/v2/api-docs`
    - Swagger UI will look like:



>>>>> FIGURE...#1

    
## Quick Start
- Prequiresites: Java, Docker, CentOS 7,  PSQL 
- Repository: git clone and mvn build
- Start Springboot app using a shell script
- Define environment variables for IEX token and psql username, password
- How to consume REST API? (Swagger screenshot and postman with OpenAPI Specification, e.g. http://35.231.122.184:5000/v2/api-docs

### REST API Usage
**Swagger** or **Postman**
- Swagger is an end point created using REST API for the users to identify application's core functions and testing them. 
- Generally front end developers use swagger end point created by backend developers.
## Architecture Diagram:
- The application has several layers within app and it implements Apache Tomcat servlet that handles server management request for us. For the database storage in the back-end PostgreSQL is used via port 5432.

>>>>>>> FIGURE#2


### [Layers]

-   **SpringBoot Framework** with **Apache Tomcat**: The framework used to manage the application and its dependencies so when application is ready its easy for developer to deploy it to the Swagger-UI. It uses Apache Tomcat webservlet application to map user request to correct function calls. It need very specific annotations for the dependencies and constructors to make it work. eg. @Component @Service @Configuration @Beans
    
-   **Controller Layer** - This is the layer that will directly interact with the end point. It connect to service layer to have business logic handy(available) to the user. `User` send request --> `controller` handle and pass --> `service layer`which executes operations and --> result of those methods and so on. It commonly receive HTTP requests to from user.
    
-   **Service Layer** - This layer is called business logic layer. It contains the methods that users will commonly need. Method implementation example: deposit/withdraw money. Here majority of logic is implemented. Service layer connect to DAO layer for fulfillment of user request.
    
-   **DAO Layer** - This layer has all the logics to work with the data implementation. That's why its called as DATA ACCESS OBJECT layer. The methods we make can directly access the data storage locally/remotely/repository. It implementation can *create, read, update, delete* data. It commonly send HTTP requests to server.
    
-   **Storage Layer - PSQL Database / Amazon RDS / other stack** - This layer is serving as a data storage. We can apply required schema and make tables here and using **JDBC** connection we can establish the data pipeline via *port 5432 (for PostgreSQL)*  It can store various tables(trader, account, quote, security order and other customer data.) It contains the tables and views that are primarily used to persist trader, account, quote and security order data. 
	- Trader table contains the user's personal details.
	- Account table has client's account balance information for buying selling stocks.
	- Quote table contains the quote;s information eg. price, size.

## Quote Controller
- **High-level summary**: Quote controller layer function is to connect MarketDataDao, QuoteDAO and QuoteService layers to HttpServer to the swagger endpoint
- It implements update, create and gets new quotes (from IexCloud) or existing quote (saved within the database).

 **Overall data flow example:**
`MarketDataDAO` --> call `Httpclient` --> to get live market data from `IEXcloud` (using token) --> data using IEX api call --> `HttpResponse` --> `JSONString` --> `QuoteService` -->  `IEXQuote`  --> `Quote` -->  `QuoteDAO` --> (if you want to store this data) `JDBC PSQL`  -->  to publish data to user **`QuoteController`**--> `Apache Tomcat`--> `Swagger UI` end points.

**End point:**
-   GET  `/quote/dailyList` - List all quotes available in database
-   GET  `/iex/tickerBatch/{ticker}` - Shows IEX market data for multiple stocks
-   GET   `/iex/tickerid/{ticker}` - Shows IEX market data for single stock
-   POST `/quote/ticker/{ticker}` - Adds a new ticker to the Quote database
-   PUT  `/quote/iexMarketData'` - Updates all quotes with live market data from IEX

> >>>PUT  `/quote/` - Allows user to manually update an individual quotes's Quote data in database

## Trader Controller
 - **High-level summary**: this layer implements trader account generation, deletion, update fund by deposit or withdraw functions.

**End point:**
 -   DELETE`/trader/traderId/{traderId}` - Deletes trader for the given id ( if account balance and position is clear) 
-   POST`/trader/` - Creates new trader
-   PUT`/trader/deposit/traderId/{traderId}/amount/{amount}` - Adds amount to trader's account
-   PUT`/trader/withdraw/traderId/{traderId}/amount/{amount}` - Withdraws amount to trader's account

  
## Order Controller

 - **High-level summary**: this layer implements the function for trader to performs buy/sell stocks (upon conditions verification).
POST`/order/marketOrder` - Buy or sell stock
 
## App controller
- **High-level summary**: this layer has no practical functionality except testing app's health (eg. port, url, local environment working correctly or not?)
-  GET `/health` to verify the SpringBoot app's health, make sure is up and running?

> >>>>## Optional(Dashboard controller)
> >>>>Uploading before :10-Aug-2019 10:00 AM



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


## Requirements:
1. Environment variables:
-	IexCloud account created to get market data using token provided.
-	If required, you can setup`IEX_PUB_TOKEN` ` PSQL_HOST` `PSQL_USER ` `PSQL_PASSWORD` `PSQL_PORT` `SPRING_PROFILES_ACTIVE`  in bash using `vi ~./bash_profile`
2. TBD

## Changes Log:
1. added Requirements and setup for environment variables.
2. Added Dashboard controller.
3. Created Startup script for the 1st time app run and 2nd time run
4. Troubleshoot guidelines for the docker and deployments.
5. Added additional J Unit 4 tests.
6. Bugfix in `TraderDAO.java` and `Trader.java` pojo solved for the date(D.O.B.) to string casting.

-------------------




