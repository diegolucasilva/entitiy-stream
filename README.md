# entitiy-stream
Intercepts objects when being saved on a database asynchronously.


 ## Abstracts components responsability
- **TransactionSaveInterceptor**: Intercept the return object after some entity was save in a database.
- **EntitySerializer**: Serializer a entity that was intercept in some format.
- **DataWriter**: Write the serialized object in some output.

It´s possible to extends this interfaces and creates other types of interceptors. For example, we could create a interceptor deleted entites from DynamoDB, serializer this objects and write in a s3 bucket.


## Component structure
This implementions is responsable to intercept entities objects after saved in a redis hash. This objects are serialized in json format. After this, it´s write in a redis stream:
- **AspectRedisOnSave**: Intercept saved objects(spring-data-redis) in a redis hash.
- **EntityJsonRdbSerializer**: Serializer the intercepted entity object in json format.
    - **EntityJsonRdbSerializer**: Replace the propertie name using some strategy.
    - **EntityJsonRdbSerializer**: Replaces nested objects with their respective id. 
    - **EntityJsonRdbSerializer**: More specific components 


- **RedisStreamWriter**: Write the serialized object in a redis stream.

![Diagram](https://github.com/diegolucasilva/entitiy-stream/blob/master/entity-stream.jpg)

 
 
 
 
 
 
  ## 
 
 



