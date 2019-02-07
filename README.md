### описание сервиса
<li> Cервис, который принимает заявки на обработку, выполняет их асинхронно и возвращает полученный результат.
Первая точка входа в сервис - это метод, который принимает извне заявку на обработку.
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
    
    Jsvs 8
    Spring Boot 2
    Spring AMQP
    PostgreSQL 11
    Flyway
    Hibernate
    
    JUnit
    EmbeddedPostgreSQL for testing
    testContainers for up RabbitMQ in test

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
<br> -Dspring.profiles.active=profile_name<br>
файл с настройками должен полностью повторять струкутру переопределенного
   
### запуск приложение через gradle     

    docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
    gradlew clean bootRun -Dspring.profiles.active=dev (or smth other profile name)

### запуск приложение через docker
Собрать образ
    
     docker build -f Dockerfile -t resolver .
     
Запустить
     
     docker-compose -f docker-compose.yml -f docker-compose.docker.yml up
        
### примеры валидных запросов:
Примеры валидных запросов:

    curl
    -d '{"type":"COMPANY", "directorName":"Frank", "directorSurname":"Gallager", "companyName":"SBPT"}'
    -H "Content-Type: application/json"
    -X POST http://localhost:8080/application

    curl
    -d '{"type":"INDIVIDUAL", "name":"Frank", "surname":"Gallager", "phone":"+79990001122"}'
    -H "Content-Type: application/json"
    -X POST http://localhost:8080/application
    
    curl -i
    -H "Accept: application/json"
    -H "Content-Type: application/json"
    -X GET http://localhost:8080/application/1
 
