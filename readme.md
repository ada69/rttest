# Rt Test #

## О приложении ##

Пример агрегации данных из нескольких сервисов

## Запуск приложения ##

Для работы требуется **jre8 (и выше)**.  

Файл project.properties расположить рядом с rttest-0.0.0.jar

в project.properties настраивается url сервиса и параметры прокси (при необходимости):

videouri=http://www.mocky.io/v2/5c51b9dd3400003252129fb5

server.port=8080

proxyhost=ip прокси

proxyport=порт прокси

user=USER

password=PASSWORD

из командной строки запустить

java -jar rttest-0.0.0.jar --spring.config.location=file:./project.properties

В rest клиенте (или в браузере) набрать

http://localhost:8080/rttest/cameras

## Дополнительное описание ##

асинхронное взаимодействие реализовано с помощью фреймворка [akka](http://akka.io)

в примере нет реализации обработки ошибок, например, отсутствие связи с сервисами


