<h3>Данный сервис принимает заявки на обработку, выполняет их асинхронно и возвращает полученный результат.</h3>

### описание сервиса
<li> Первая точка входа в сервис - это метод, который принимает извне заявку на обработку.
Заявки бывают разных типов, отличающихся набором полей, и реализованы с использованием наследования.
HTTP адрес для приема заявок один для всех типов.
При поступлении заявки возвращается положительный ответ о ее приеме, не дожидаясь обработки этой заявки.
Сама обработка происходит асинхронно. Данный механизм реализован с использованием очереди в RabbitMQ.
В качестве метода, осуществляющего обработку заявки, для простоты используется метод:

    public Request process(Request request) throws Exception { 
        Thread.sleep(1000);
        return request;
    }

Считаем, что структура запроса и результата всегда совпадает.
Далее результат выполнения сохраняется в базу данных.</li>

<li> Вторая точка входа в сервис - это метод получения результатов заявки по id, если она обработана, или информации о том, что результат по заявке не готов. В случае готовности контролер должен вернуть сохраненный ранее объект. </li>

Основной стек технологий:
    
    Java 8
    Spring Boot 2
    Spring AMQP
    PostgreSQL 11
    Flyway
    Hibernate
    
    JUnit
    testContainers - RabbitMQ for testing
    embeddedPostgres for testing

### собрать приложение 

    gradle clean bootWar
  
### запуск тестов     
    
    gradlew test

### запуск приложения
Выполнить команду

    docker-compose -f docker-compose.yml -f docker-compose.dev.yml up

Запуск приложения выполнять из директории build/libs

    java -Dspring.profiles.active=dev -jar resolver-1.0.war

Для переопределения файла со свойсвами при старте указать параметр 
    
    -Dspring.profiles.active=profile_name
    
Так же желательно указать следующие значения
    
    -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    -Dspring.rabbitmq.host=localhost
    -Dserver.port=port"
    
файл с настройками должен полностью повторять струкутру переопределенного
   
Приложение по дефолту поднимается на 8888 порт
### запуск приложение через docker
Собрать образ
     
     gradle clean bootWar
     docker build -f Dockerfile -t resolver .
     
Запустить
     
     docker-compose -f docker-compose.yml -f docker-compose.docker.yml up
        
### примеры валидных запросов:
Примеры валидных запросов:

    curl
    -d '{"type":"COMPANY", "directorName":"Frank", "directorSurname":"Gallager", "companyName":"SBPT"}'
    -H "Content-Type: application/json"
    -X POST http://localhost:8888/application

    curl
    -d '{"type":"INDIVIDUAL", "name":"Frank", "surname":"Gallager", "phone":"+79990001122"}'
    -H "Content-Type: application/json"
    -X POST http://localhost:8888/application
    
    curl -i
    -H "Accept: application/json"
    -X GET http://localhost:8888/application/1
    
    curl -H -X GET http://192.168.99.100:8888/actuator/health //статус сервиса
 
