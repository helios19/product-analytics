Setup Instructions
--

**Fat-Jar**

The microservice product-analytics has been implemented using gradle wrapper. Hence, to build it, simply run the following command
in a terminal, at the root of the source folder to generate the runnable fat jar:

```
./gradlew clean build
```

NB: This command will run unit and integration tests before packaging
the fat jar. If you wish to speed up the build and discard them,
simply add this `-x test` option to the above command. Also, take note that,
given the list of third party dependencies, the build process might vary from
1 to 4 mins depending on your local gradle repository and your network bandwidth.

Then, to run the application, simply type:

```
java -jar <SOURCE_FOLDER>/build/libs/product-analytics-1.0.jar
```

Alternatively, you could also use the bootRun gradle task to run the application (skipping the jar packaging altogether):

```
./gradlew bootRun
```

The endpoints to retrieve the transaction summary in json format will be available at the following URLs:

```
GET http://localhost:8081/transaction-summary/all
```

Invoking the above endpoint should give you the following transaction summary:
```
[{"clientInformation":"CL432100020001","productInformation":"SGXFUNK20100910","totalTransactionAmount":46.0,"date":"2010-08-19T14:00:00.000+00:00"},{"clientInformation":"CL432100030001","productInformation":"CMEFUN120100910","totalTransactionAmount":-79.0,"date":"2010-08-18T14:00:00.000+00:00"},{"clientInformation":"CL123400020001","productInformation":"SGXFUNK20100910","totalTransactionAmount":-52.0,"date":"2010-08-19T14:00:00.000+00:00"},{"clientInformation":"CL123400030001","productInformation":"CMEFUNK.20100910","totalTransactionAmount":-215.0,"date":"2010-08-18T14:00:00.000+00:00"},{"clientInformation":"CL123400030001","productInformation":"CMEFUN120100910","totalTransactionAmount":285.0,"date":"2010-08-18T14:00:00.000+00:00"}]
```

Similarly, the endpoint to retrieve the transaction summary in csv format will be available at the following URLs:

```
GET http://localhost:8081/transaction-summary/export-csv
```

**Docker**


Another option to run this application would consist in packaging it as container
and run it through a Docker installation. Please note this approach will require you
to download and install Docker (refer to the official online documentation for this).

When Docker has been installed on your machine, just run the following gradle task
to generate the docker image:

```
./gradlew dockerBuildImage
```

Then, after a few minutes, if the task run successfully, you should be able to see the application docker image
created in the local docker registry with the following name: "abn-amro/product-analytics".

To run the application, just type in a terminal the following docker command:

```
docker run abn-amro/product-analytics
```


**UI**

Once the application has started, to access the UI, just open your favorite browser and enter `http://localhost:8081`. Make sure port `8081`
is not used by another process. Then you should be able to access the Transaction Summary Report main page.
> **_NOTE:_**  The UI part of the application might not fully work yet.

***


Requirement assumptions
--

Given the provided high-level requirements the following list of assumptions have been made while designing the solution:

- Even though no detailed technical requirements have been provided, the following technical points have been
considered while implementing the solution:

    - Discoverability and consistency provided by HATEAOS and HAL specification
    (only implemented for error handling using `VndError` response object)
    - Vulnerability of the system through common XSS attacks
    - Performance and scalability of the system
    - Testability of the system from the start through BDD

***

Design and architecture decisions
--

The solution implemented has been designed in a microservice architecture model,
even though only one fat jar (dedicated to the transaction resource and related summary details) has been produced.

This type of architecture provides to the application several benefits among, being loosely coupled
with other services, stateless, scalable and resilent to failures.

Hereafter is a shortlist of the other technical aspects characterizing the application:

**Microservice using Spring Boot**

Spring Boot being one of the most mature framework and currently leading the trend in microservice applications,
the decision has been made to use it along with Java given the wide support Spring has for this language.
However, note that Scala or Kotlin could also have been good alternatives.

**HATEAOS**

HATEOAS along wit HAL specification have been used mainly for error messages using VndError response object.

**Security**

A XSS filter (encoding and stripping out malicious code from request parameter) has been added to the system
and being used to check submitted request and header values.

**Performance**

A particular emphasis was place on the performance aspect of the system through
the use of an in-memory database - namely h2, caching strategies (using guava cache), SPA AngularJS and localstorage
to minimize network round trips.

**SPA UI**

The UI of this application has been implemented using AngularJS 1.3 for which
the source can be found in the `resources/static` folder of this project. The UI
pages are responsive and built using `Bootstrap` making the view net and concis.

**BDD and Testability**

The system has been coded entirely using BDD approach with spring-test and RESTAssured and Spock.
