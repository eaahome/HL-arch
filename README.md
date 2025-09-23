# Назначение

Учебный проект High-load architect

# Requirements

* openjdk version "21.0.7"
* Apache Maven 3.9.9
* mariadb-server-core-10.3

Могут подойти другие версии, но с ними не тестировалось.


# Build

* Собрать проект ```mvn clean install```

* Собрать проект для запуска в docker: ```mvn spring-boot:build-image -Dspring-boot.build-image.imageName=erzinname/high-load-social-net```
или ```./docker.build ```

# Install

## Общая часть для всех вариантов запуска

* Создать пользователя БД "hl1"
* Создать под ним БД "hl1"
* Инициализировать БД скриптом createdb.sql

## Подготовка для локального запуска

* Скопировать файл ```local.properties.template``` в ```local.properties```, 
* положить его в каталог, из которого будет запускаться jar, 
* прописать пароль к БД в нем

## Подготовка для запуска в Docker

* Скопировать файл ```local.env.template``` в ```local.env```
* положить его в каталог, из которого будет запускаться jar, 
* прописать пароль к БД в нем


# Run

## Локальный запуск

``` java -jar HighLoadSocialNet-1.0-SNAPSHOT.jar```

При необходимости использования IPv4:

``` java -jar -Djava.net.preferIPv4Stack=true HighLoadSocialNet-1.0-SNAPSHOT.jar```

## Запуск в Docker

```./docker.run```


# Test

Коллекция для Postman: [HighLoadSocialNet/src/main/resources/HighLoadSocialNet basics.postman_collection.json](https://github.com/eaahome/HL-arch/blob/master/HighLoadSocialNet/src/main/resources/HighLoadSocialNet%20basics.postman_collection.json)

# TODO

* Автоматическая инициализация БД
* Контейнеризация в docker

