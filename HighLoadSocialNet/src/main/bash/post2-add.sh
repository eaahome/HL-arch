curl -v -X 'POST' \
  'http://localhost:8080/post/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjA0MjE1NTcsImV4cCI6MTc2MDUwNzk1NywibG9naW4iOiJ1c2VyMiJ9.6OFKvqvQcJV1YVHgG_a_R-bdTtSmY-8wZDNzPR2m3JQ' \
  -d '{
  "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa."
}'

echo
