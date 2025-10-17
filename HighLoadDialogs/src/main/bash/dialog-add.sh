curl -vX 'POST' \
  'http://localhost:8080/dialog/user2/send' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjA3MDU1NzksImV4cCI6MTc2MDc5MTk3OSwibG9naW4iOiJ1c2VyMSJ9.O2aRJmo43Ox_zWgOH9NHsrFQylQnZQzH_8d47CL17qE' \
  -d '{
  "text": "Привет, как дела?"
}'

echo
