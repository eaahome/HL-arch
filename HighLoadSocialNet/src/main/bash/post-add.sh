curl -v -X 'POST' \
  'http://localhost:8080/post/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjAzODUzOTMsImV4cCI6MTc2MDQ3MTc5MywibG9naW4iOiJ1c2VyMSJ9.fsWSScqMDgp8q865tJpbqMZ3ZGbhoeN5-x7GUv2N31g' \
  -d '{
  "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa."
}'

echo
