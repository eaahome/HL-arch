curl -v -X 'POST' \
  'http://localhost:8080/post/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjAyNjIzNjQsImV4cCI6MTc2MDM0ODc2NCwibG9naW4iOiJ1c2VyMSJ9.avKzz-vQg4XaidCYM0vgaA51aCxxKL6uim9ApB7qaFs' \
  -d '{
  "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa."
}'

echo
