## Deci se procedeaza in felul urmator:

- Instalezi **docker** (sa poti da comenzile din terminal).
- Executi comanda `docker compose up --build`. Asigura-te ca esti in folderul in care se afla fisierele `Dockerfile`
  si `docker-compose.yml`

## Chestii de retinut:

- O sa dureze mult prima data, dar apoi ar trebui sa fie ok.
- Nu ar mai trebui modificat continutul `src/main/resources/application.properties`. Pentru schimbare portului mergi
  in `docker-compose.yml` si schimbi `services.server.ports` din `8080:8080` in `<portul dorit>:8080`.

## ENDPOINT-URI

- **Address**
    - `lisAll`: GET http://localhost:8080/addresses
- **Admin**
    - `listAll`: GET http://localhost:8080/admins
- **Client**
    - `list`: GET http://localhost:8080/clients?start=0&count=3
    - `findOne`: GET http://localhost:8080/clients/id
    - `save **NEIMPLEMENTAT**`: POST http://localhost:8080/clients {
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "parrwordC":
        - "balance":
        - "idAddress": }
    - `update **NEIMPLEMENTAT**`: PUT http://localhost:8080/clients/id {
        - "id":
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "parrwordC":
        - "balance":
        - "idAddress": }
    - `delete`: DELETE http://localhost:8080/clients/id
- **Employee**
    - `list`: GET http://localhost:8080/employees?start=0&count=3
    - `findOne`: GET http://localhost:8080/employees/id
    - `save **NEIMPLEMENTAT**`: POST http://localhost:8080/employees {
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "parrwordC":
        - "balance":
        - "idAddress": }
    - `update **NEIMPLEMENTAT**`: PUT http://localhost:8080/employees/id {
        - "id":
        - "lastName":
        - "firstName":
        - "phoneNumber":
        - "email":
        - "parrwordC":
        - "balance":
        - "idAddress": }
    - `delete`: DELETE http://localhost:8080/employees/id
- **Invoice**
    - `list`: GET http://localhost:8080/invoices?start=0&count=3
    - `findOne`: GET http://localhost:8080/invoices/id
    - `save`: POST http://localhost:8080/invoices {
        - "idClient":
        - "idEmployee":
        - "categoryType":
        - "penaltyPoints":
        - "listItems": [
            - {"id": , "number": },
            - {"id": , "number": } ] }
- **Item**
    - `listAll`: GET http://localhost:8080/items
    - `findAllByCategory`: GET GET http://localhost:8080/items/findAllByCategory?categoryType=