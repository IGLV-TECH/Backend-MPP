## Deci se procedeaza in felul urmator:

- Instalezi **docker** (sa poti da comenzile din terminal).
- Executi comanda `docker compose up --build`. Asigura-te ca esti in folderul in care se afla fisierele `Dockerfile` si `docker-compose.yml`

## Chestii de retinut:

- O sa dureze mult prima data, dar apoi ar trebui sa fie ok.
- Nu ar mai trebui modificat continutul `src/main/resources/application.properties`. Pentru schimbare portului mergi in `docker-compose.yml` si schimbi `services.server.ports` din `8080:8080` in `<portul dorit>:8080`.
