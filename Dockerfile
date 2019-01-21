#FROM openjdk:8-jdk-alpine
FROM jeanblanchard/java:jdk-8


#RUN apk --update --no-cache add bash curl linux-headers build-base autoconf automake libtool apr-util apr-util-dev openssl-devel

VOLUME /usr/local/autocomplete-curd
ADD target/autocomplete-crud-1.0.0-SNAPSHOT.jar /usr/local/autocomplete-curd/autocomplete-curd.jar
ADD key/autocomplete-demo-pub-sub.json /usr/local/autocomplete-curd/autocomplete-demo-pub-sub.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENV GOOGLE_APPLICATION_CREDENTIALS="/usr/local/autocomplete-curd/autocomplete-demo-pub-sub.json"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-curd/autocomplete-curd.jar"]

