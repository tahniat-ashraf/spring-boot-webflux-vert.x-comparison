

##Commands to Run
mvn clean package exec:java <br/>

Test P2P Messaging : localhost:8082 <br/>

Test Pub-Sub Messaging : localhost:8080 <br/>

###Test Student CRUD

curl --location --request POST 'localhost:4568/studentCreate' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "Tasnuva",
"lastName": "Ashraf",
"age": 22
}'

curl --location --request PUT 'localhost:4568/studentUpdate' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "Tasnuva",
"lastName": "Umama",
"age": 23
}'

curl --location --request GET 'localhost:4568/studentList'

curl --location --request GET 'localhost:4568/student?firstName=Tahniat'

curl --location --request DELETE 'localhost:4568/studentDelete?firstName=Tahniat'


