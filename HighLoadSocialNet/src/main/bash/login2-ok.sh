curl http://localhost:8080/login \
 -v -XPOST \
 -H "Content-Type: application/json" \
 -H "Accept: application/json" \
 -d'{
  "id": "user2",
  "password": "123"
}'

echo
