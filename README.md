# Ktornament

- Ktor server with CRUD API to handle players ranking in a tournament.
- Angular client to interact with the server.

## Requirements
- Java 17
- Install MongoDB: https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/#installing-mongodb-5.0-edition-edition
- `./gradlew build`
- `cd front && npm i`

## Run

```shell
cd scripts
./run.sh
```
`^C` to stop front client.
`kill <pid>` to stop server (pid in `logs/back.pid`).

## What next
- Unique pseudo
