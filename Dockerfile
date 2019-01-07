FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/local/autocomplete-curd
VOLUME /usr/local/autocomplete-curd
ADD target/autocomplete-crud-1.0.0-SNAPSHOT.jar /usr/local/autocomplete/autocomplete-curd.jar
ADD key/minsoojun-222707-7a0dfe1ba9a0.json /usr/local/autocomplete-curd/minsoojun-222707-7a0dfe1ba9a0.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-curd/autocomplete-curd.jar"]

