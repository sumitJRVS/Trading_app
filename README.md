## Trading REST API

### Introduction:

    -- This trading REST API SpingBoot Java app is built using micro-services. The SpingBoot handles internal dependencies for the app here. The app is used to find store stock prices from live/offline market and do trade within account and also buy buy stocks.
    -- The user can save its stock data with the account and obtain later market quote in future for its portfolio. The account has some basic features to deposit-withdraw funds, create-delete account functions.
    -- The data is obtained by IEX plateform and user need to create an account on the webpage[https://iextrading.com/] and obtaine the key to paste it in the environment variable within the app.
    -- Swagger v2 webpage(port 80, can be accessible by http request-webpage-browser/cell) will be ultimately used by frontend developers to built the UI webpage efficiently.

#### Diagram architecture:

The application has several layers within app and it implements Apache Tomcat servlet that handles server management request for us. For the database storage in the back-end PostgreSQL is used via port 5432.

Diagram


### Changes Log: