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
* Скопировать файл ```local.properties.template``` в ```local.properties```, положить его в каталог, из которого будет запускаться jar, и прописать пароль к БД в нем

# Run

``` java -jar HighLoadSocialNet-1.0-SNAPSHOT.jar```

При необходимости использования IPv4:

``` java -jar -Djava.net.preferIPv4Stack=true HighLoadSocialNet-1.0-SNAPSHOT.jar```

# Test

Коллекция для Postman: [HighLoadSocialNet/src/main/resources/HighLoadSocialNet basics.postman_collection.json](https://github.com/eaahome/HL-arch/blob/master/HighLoadSocialNet/src/main/resources/HighLoadSocialNet%20basics.postman_collection.json)

# TODO

* Автоматическая инициализация БД
* Контейнеризация в docker

