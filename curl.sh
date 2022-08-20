curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":"1","describe":"arroz"}' \
  http://localhost:8080/products

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":"2","describe":"feijao"}' \
  http://localhost:8080/products

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":"3","describe":"farinha"}' \
  http://localhost:8080/products