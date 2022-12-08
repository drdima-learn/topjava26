curl http://localhost:8080/topjava/rest/meals/100009 | json_pp

curl http://localhost:8080/topjava/rest/meals | json_pp

curl http://localhost:8080/topjava/rest/meals/100009 | json_pp

curl -X DELETE http://localhost:8080/topjava/rest/meals/100009

curl http://localhost:8080/topjava/rest/meals

curl -X POST http://localhost:8080/topjava/rest/meals \
-H 'Content-Type: application/json' \
-d '{"dateTime":"2022-12-06T20:00:00","description":"Ужин New","calories":999,"user":null}'



curl -X PUT http://localhost:8080/topjava/rest/meals/100009 \
-H 'Content-Type: application/json' \
-d '{"id": 100009,"dateTime": "2022-12-06T20:00:00","description": "Ужин Updated","calories": 999}'


curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-31&endTime=20:00:01" | json_pp

