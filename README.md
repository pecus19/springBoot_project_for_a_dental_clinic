# Dental clinic's website

The website will allow clients to make appointments with dentists, find closest clinic from available ones, and plan
operation costs based on their personal information, such as medical insurance and client status. Reference:
https://www.keskhaigla.ee/clinics/dental-clinic/?lang=en.

Features:

Creation of a website for a dental clinic, where you can learn about the clinic itself and about specialists, and sign
up for various appointments with a dentist. The specialists will see which patients should come and at what time. The
clinic will also display a list of all the clients that have visited the clinic and what they signed up for

## Spring's application.properties

- **spring.datasource.url=jdbc:mysql://localhost:3306/db**  
  URL address for connecting to the MySQL Database
- **spring.datasource.username=user**
  Database user
- **spring.datasource.password=password**
  User password
- **spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver**

  The driver used for the connection
- **spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect**

  Forces Hibernate to generate the appropriate SQL for the selected database
- **spring.jpa.show-sql=false**

  Disables logging of sql statements

- **spring.jpa.hibernate.ddl-auto=create**
  Means that when the server is run, the database(table) instance is created

## Local setup

### Getting source code

- Clone the backend repository (https://gitlab.cs.ttu.ee/agorbo/iti0302-backend)
- Checkout the 'main' branch

### Run the DB and Spring application

- Create docker compose file (docker-compose.yml) and insert contents of the 'docker-compose-server.yml' from repo into
  it
- Using Docker, run the compose file ('docker compose down', 'docker compose pull', 'docker compose up'), it sets up the
  MySQL DB and Spring application
- Application will be available at http://localhost:8080/

## Dependencies

- Docker(v20.10.17)
- Docker compose (v2)
- Java (v17.0.2)
- Gradle (v2.7.3)

## Deployment steps

### Locating the working directory

- Connect to the server with SSH (ssh -l ubuntu 193.40.255.20)
- Change directory ('cd /opt/dentalclinic-backend')

### Running DB and Spring application

- 'docker compose down' to stop DB and Spring
- 'docker compose pull' to update container images
- 'docker compose up -d' to start DB and Spring

### Install, configure and run NGINX

#### Install:

- sudo apt update
- sudo apt install nginx

#### Configure:

- at file /etc/nginx/sites-enabled/default
    - comment out line 'listen [::]:80 default_server;'
- at file /etc/nginx/sites-available/default:
    - add to "server { ... }"

```
location /api/ {  
  # First attempt to serve request as file, then  
  # as directory, then fall back to displaying a 404.  
  proxy_pass http://localhost:8080/;  
}  
location / {  
  proxy_pass http://localhost:8081;  
}
```

#### Run:

- 'sudo service nginx start' to start nginx
- 'sudo systemctl restart nginx' to restart nginx

## Tasks progress:

- [x] Bonus Task 1
- [x] Bonus Task 2
- [x] Task 1
- [x] Task 2
- [ ] Task 3

## Authors and acknowledgment

Team members: Andrey Gorbovskiy, Danila Kirejev, Nikita Kirejev, ja Viktorija Mištšenko.  
Team mentor: Mart Hütt.  
Course teacher: Siim Rebane.

## License

No license is used at the moment.

## Project status

Project is in an active development.
