# Назначение

Учебный проект High-load arhitect

# Requirements

* openjdk version "21.0.7"
* Apache Maven 3.9.9
* mariadb-server-core-10.3

Могут подойти другие версии, но с ними не тестировалось.


# Build

* Прописать логин и пароль пользователя БД в application.properties
* Собрать проект ```mvn clean install```

# Install

* Создать пользователя БД "hl1"
* Создать под ним БД "hl1"

* Инициализировать БД скриптом createdb.sql

# Run

``` java -jar HighLoadSocialNet-1.0-SNAPSHOT.jar```

# TODO

* Автоматическая инициализация БД
* Контейнеризация в docker

