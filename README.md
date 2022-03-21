# Simple Library with Spring

Java version: Java 11  
Database: MySQL  
Documentation: Swagger (https://swagger.io/)   
HTTP Client: 
- Retrofit (https://square.github.io/retrofit/)  
- WebClient
- RestTemplate
Token Feature (Create & Validate): TOTP using JOTP (https://github.com/amdelamar/jotp)

#### Schema Service

 ![Schema](schema.png?raw=true "Schema")
 
We have 2 service:
 - Library Service: This service will handle request from user  
 - Data Service: This service will connect to database and serve other service to get data from database.
 
 Available process
 Library Service:
  - Search book
  - Search order
  - Get detail order
  - Submit order
  - Return order (Return book)
 
 Data Service: CRUD to table book and order
  
 Documentation Link: http://localhost:8081/swagger-ui.html         
 Postman collection: SimpleLibrary.postman_collection.json
 
 #### Database Design
 ```        
 book
 -------------------------------------  
 |           | Type    | Description |
 -------------------------------------
 | book_id   | Integer | PK          |
 | title     | Varchar |             |
 | author    | Varchar |             |
 | year      | Varchar |             |
 | qty       | Integer |             |
 ------------------------------------- 
 
 order
 -------------------------------------- 
 |            | Type    | Description |
 --------------------------------------
 | order_id   | Integer | PK          |
 | book_id    | Integer |             |
 | username   | Varchar |             |
 | userphone  | Varchar |             |
 | start_date | Date    |             |    
 | end_date   | Date    |             |
 | status     | Varchar | OPEN/CLOSED |
 --------------------------------------            
 ```  
 
