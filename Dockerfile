FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/local/autocomplete-curd
VOLUME /usr/local/autocomplete-curd
ADD target/autocomplete-crud-1.0.0-SNAPSHOT.jar /usr/local/autocomplete-curd/autocomplete-curd.jar
ADD key/gcp-pub-sub.json /usr/local/autocomplete-curd/gcp-pub-sub.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
RUN export GOOGLE_APPLICATION_CREDENTIALS="/usr/local/autocomplete-curd/gcp-pub-sub.json"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-curd/autocomplete-curd.jar"]

