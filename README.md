# atm
In order to start this spring boot application you can navigate to src/main/java/com/springboot/first/app/controller/SpringbootFirstAppApplication.java.
Within this class if you run the main method the spring boot application will start.

To initalise the ATM and set it's available balance and notes you can send the following request:
GET localhost:8080/account/

If you'd like to check the balance of an account in the system you can send the following request:
POST localhost:8080/account/balance, within the body of the request include the pin of the account you wish to check

If you'd like to make a withdrawal from an account you can send the following request:
POST localhost:8080/account/withdraw

The body of this request should look as follows:
{
    "withdrawAmount": the amount you want to withdraw in an int form,
    "accountPin": the pin of the account you want to withdraw from
}

Below is an example request to take 50 euro out of the account with the pin 1234.
{
    "withdrawAmount": 50,
    "accountPin": 1234
}

If you'd like to run this application via the DockerFile you can do so by running the following commands in the terminal of your IDE:

docker build -t {name of the image} .
docker run -p 8080:8080 {name of the image}

When running the tests for this application you can do so here:
src/test/java/com/springboot/first/app/AccountServiceTest.java

These tests are run against the service level of this application. If you run the tests from the class level some of them will fail because
they don't run in isolation.

If you run each unit test indiviually they will all pass without issue.
