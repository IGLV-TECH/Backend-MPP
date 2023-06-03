## Deci se procedeaza in felul urmator:

- Instalezi **docker** (sa poti da comenzile din terminal).
- Executi comanda `docker compose up --build`. Asigura-te ca esti in folderul in care se afla fisierele `Dockerfile`
  si `docker-compose.yml`

## Chestii de retinut:

- O sa dureze mult prima data, dar apoi ar trebui sa fie ok.
- Nu ar mai trebui modificat continutul `src/main/resources/application.properties`. Pentru schimbare portului mergi
  in `docker-compose.yml` si schimbi `services.server.ports` din `8080:8080` in `<portul dorit>:8080`.

## ENDPOINTS

- **LOGIN & LOGOUT** for `Client`, `Employee` and `Admin`


- **Client**
  - `withdraw`: PUT http://localhost:8080/clients/id/withdraw?amount=
    - auth by **CLIENT**
  - `list`: GET http://localhost:8080/clients?start=&count=
    - auth by **ADMIN**
  - `findById`: GET http://localhost:8080/clients/id
    - auth by **ADMIN**
  - `save`
    - auth by **ADMIN**
    - : POST http://localhost:8080/clients {
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "password":
        - "balance":
        - "address": {
          - "county":
          - "city":
          - "street":
          - "number":
          - "ZIPCode": }
  - `update`
    - auth by **ADMIN**
    - : PUT http://localhost:8080/clients/id {
        - "id":
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "password":
        - "balance":
        - "address": {
          - "county":
          - "city":
          - "street":
          - "number":
          - "ZIPCode": }
  - `delete`: DELETE http://localhost:8080/clients/id
    - auth by **ADMIN**
- **Employee**
  - `list`: GET http://localhost:8080/employees?start= &count=
    - auth by **ADMIN**
  - `findById`: GET http://localhost:8080/employees/id
    - auth by **ADMIN**
  - `save`
    - auth by **ADMIN**
    - : POST http://localhost:8080/employees {
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "password":
        - "address": {
          - "county":
          - "city":
          - "street":
          - "number":
          - "ZIPCode": }
  - `update`
    - auth by **ADMIN**
    - : PUT http://localhost:8080/employees/id {
        - "id":
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "password":
        - "address": {
          - "county":
          - "city":
          - "street":
          - "number":
          - "ZIPCode": }
  - `delete`: DELETE http://localhost:8080/employees/id
    - auth by **ADMIN**
- **Invoice**
    - `list`: GET http://localhost:8080/invoices?start=&count=
      - auth by **ADMIN** and **CLIENT**
    - `findById`: GET http://localhost:8080/invoices/id
      - auth by **ADMIN** and **CLIENT**
    - `save`
      - auth by **EMPLOYEE**
      - : POST http://localhost:8080/invoices {
          - "idClient":
          - "idEmployee":
          - "categoryType":
          - "penaltyPoints":
          - "listItems": [
              - {"id": , "number": },
              - {"id": , "number": } ] }
- **Item**
    - `findAllByCategory`: GET http://localhost:8080/items/findAllByCategory?categoryType=
      - auth by **EMPLOYEE**