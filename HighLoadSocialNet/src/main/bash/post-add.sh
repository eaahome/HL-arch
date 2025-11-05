curl -v -X 'POST' \
  'http://localhost:8080/post/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjE5MzkxMjIsImV4cCI6MTc2NDUzMTEyMiwibG9naW4iOiJ1c2VyMSJ9.A5OL_RcDzmh126SC6o2PfjuZXIPZ8q0vZczXVsHzuJ4' \
  -d '{
  "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa."
}'

echo
