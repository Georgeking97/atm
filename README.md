# atm
In order to start this spring boot application you can run the following command: mvn spring-boot:run <br />

To initalise the ATM and set it's available balance and notes you can send the following request: <br />
GET localhost:8080/account/ <br />

If you'd like to check the balance of an account in the system you can send the following request: <br />
POST localhost:8080/account/balance, within the body of the request include the pin of the account you wish to check <br />

If you'd like to make a withdrawal from an account you can send the following request: 
POST localhost:8080/account/withdraw <br />

The body of this request should look as follows: <br />
{ <br />
    "withdrawAmount": the amount you want to withdraw in an int form, <br />
    "accountPin": the pin of the account you want to withdraw from <br />
} <br />

Below is an example request to take 50 euro out of the account with the pin 1234. <br />
{ <br />
    "withdrawAmount": 50, <br />
    "accountPin": 1234 <br />
} <br />

If you'd like to run this application via the DockerFile you can do so by running the following commands in the terminal of your IDE: <br />

docker build -t {name of the image} . <br />
docker run -p 8080:8080 {name of the image} <br />

When running the tests for this application you can do so here: <br />
src/test/java/com/springboot/first/app/AccountServiceTest.java <br />

These tests are run against the service level of this application. If you run the tests from the class level some of them will fail because <br />
they don't run in isolation. <br />

If you run each unit test indiviually they will all pass without issue.
