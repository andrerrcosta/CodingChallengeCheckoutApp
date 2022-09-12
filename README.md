# CodingChallenge

# Description
This software was developed with spring boot through Hexagonal/clean architectures
and Domain Driven Design. The main technologies used here were RedisDB, H2 and Mapstruct.

# How to run
1. run on terminal: mvn clean install (mapstruct must create the mapper implementations).
2. run on terminal: sudo docker-compose -f ./infrastructure/docker-compose/redis_db.yml up (to run the redis container)
3. run on terminal java -jar ./checkout-service/checkout-container/target/checkout-container-1.0-SNAPSHOT.jar 
(to run the application or just run mvn spring-boot:run under the "./checkout-service/checkout-container/" path)

# Rest
- POST: localhost:8080/add-item
    - request: { productId: String, customerId: Long }
    - response: { items: List<BasketItemResponse>,
      total: BigDecimal, totalPromos: BigDecimal
      totalPayable: BigDecimal }
  
- POST: localhost:8080/checkout/{customerId}
  - response: { items: List<BasketItemResponse>,
      total: BigDecimal, totalPromos: BigDecimal
      totalPayable: BigDecimal }
* Obs: Use the customer id "1" that will be already on the database.

# Follow-up Questions
1. How long did you spend on the test?

- I spent about 30 hours on this test.

2. What would you add if you had more time?

- I would have finished at least the tests on entities for hashes and JPA tests
- I would have done the front-end. 

3. How would you improve the product APIs that you had to consume?

- I would have implemented the communication between the product creation
and the checkout database, implemented eventual consistency
with cached products and changed its architecture to deliver messages.
- I would create a SQL function to apply the promotions by query arguments.
(this is a good option because it would consume less resources, 
it would only require one request for the whole basket, and it 
would make it possible to add promotions through sql)

4. What did you find most difficult?

- The software does not make much sense in the point of view of its
possible architectures. It seems that the architecture was created the same
way a checkout machine works and from software perspective this is not
always the best approach.
The idea of making calls from the checkout
service exceeds its functions. The customer should add items with calls
to a client api. The client api should send a message
to the checkout containing the product informations,
the checkout should make the calculations and return the results
to the client api.

5. How did you find the overall experience, any feedback for us?

- It was a nice experience.
