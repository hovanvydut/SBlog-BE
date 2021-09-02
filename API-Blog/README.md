# SBlog
### Set up environments

```zhs
# start mysql
sudo systemctl start mysql

# start redis using docker
docker container start redis

# start mail server
npm i -g maildev
maildev

# Run appa
./mvnw spring-boot:run
```

- Static file:  
```zhs
http://localhost:3000/assets/static/uploaded/images/avt_20210709211905_1203788484.jpg
```

### Migration
 ```zhs
mvn flyway:clean -Dflyway.configFiles=flyway.conf flyway:migrate -X
```

### API Document

```aidl
http://localhost:3000/swagger-ui/index.html
```

### Technology
[x] Spring Boot
[x] Redis
[x] BlackBlaze Cloud
[x] Docker