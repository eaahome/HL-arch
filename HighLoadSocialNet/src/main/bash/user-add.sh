curl -v -X 'POST' \
  'http://localhost:8080/user/register' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "first_name": "Имя",
  "second_name": "Фамилия",
  "birthdate": "2017-02-01",
  "biography": "Хобби, интересы и т.п.",
  "city": "Москва",
  "sex": "Секретная строка"
}'

echo
