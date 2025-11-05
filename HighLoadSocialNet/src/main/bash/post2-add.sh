curl -v -X 'POST' \
  'http://localhost:8080/post/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjE5MzkxNTcsImV4cCI6MTc2NDUzMTE1NywibG9naW4iOiJ1c2VyMiJ9.qU4VxLDKk9_sWcv0G75D7v6CSH4issHg2kCc8hTPeUQ' \
  -d '{
  "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa."
}'

echo
