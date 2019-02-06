### 1 настройка приложения 
Настройки соединения с базой данных:    
```sh
    раздел настройки соединения с базой данных
      host: 192.168.99.100
      port: 5432
      user: admin
      password: password
      schema: public
      database: postgres
      ddlAuto: validate
      showSql: true
    hikari:
      minPoolSize: 5
      maxPoolSize: 20
      preferredTestQuery: select 1
  ```
Настройки соединения с rabbitMQ:    
```sh
    rabbitmq:
      host: 192.168.99.100
      username: kc4kt4
      password: qwerty
  ```

### 2 собрать прилодение 
```sh
    gradle clean bootWar
```
  
### 3 запуск приложения
Выполнить команду
   ```sh
     docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
   ```
   Запуск приложения выполнять из директории build/libs
   ```sh
     java -jar resolver-1.0.war
   ```
Для переопределения файла со свойсвами при старте указать параметр 
<br> -Dspring.profiles.active=profile_name<br>
файл с настройками должен полностью повторять струкутру переопределенного
   
   ```sh
     java -Dspring.profiles.active=profile_name -jar resolver-1.0.war
   ```
   
### 4 запуск тестов     
При запуске тестов выполнить последовательно команды из корневой директории проекта
    
   ```sh
      docker-compose -f docker-compose.yml -f docker-compose.test.yml up
      gradlew test
   ```
### 5 запуск приложение через gradle     
При запуске тестов выполнить последовательно команды из корневой директории проекта

   ```sh
      docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
      gradlew clean bootRun -Dspring.profiles.active=dev (or smth other profile name)
   ```
### 6 примеры валидных запросов:
Примеры валидных запросов:
   ```sh
{
	"application": {
		"type": "COMPANY",
		"directorName": "Stefan",
		"directorSurname": "Gallager",
		"companyName": "SBPT"
	}
}

{
	"application": {
		"type": "INDIVIDUAL",
		"name": "Stefan",
		"surname": "Gallager",
		"phone": "+79990001122"
	}
}
   ```
    
