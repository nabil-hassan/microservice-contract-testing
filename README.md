# microservice-contract-testing

## Overview

Illustrates how to use contract testing techniques to verify the behaviour of REST and SOAP endpoints, and Kafka queues.

The project comprises the following modules:

  * `api-server-core-module`: Contains repository level code shared by both the SOAP and REST modules
  * `api-server-rest-module`: Exposes REST API endpoints
  * `api-server-soap-module`: Exposes SOAP API endpoints
  * `api-client-module`: Contains client code which consumes the endpoints defined in api-server-module
  * `pact-module` : Defines contract tests based on the server and client code defined in the previous 2 modules
  
## Using the API manually   

You can test the API manually via Postman, using the following:

   * `Microservice Contract Testing.postman_collection.json` - Postman collection for the server APIs
   * `microservice-contract-testing-local.postman_environment.json` - Postman environment for default local server address

