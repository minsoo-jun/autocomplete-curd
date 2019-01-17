#FROM openjdk:8-jdk-alpine
FROM jeanblanchard/java:jdk-8

MAINTAINER pjpires@gmail.com

RUN apk add --update \
        linux-headers build-base autoconf automake libtool apr-util apr-util-dev git cmake ninja go

ARG NETTY_TCNATIVE_TAG=netty-tcnative-parent-2.0.7.Final
ENV NETTY_TCNATIVE_TAG $NETTY_TCNATIVE_TAG
ENV MAVEN_VERSION 3.3.9
ENV MAVEN_HOME /usr/share/maven

RUN cd /usr/share ; \
        wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -O - | tar xzf - ;\
        mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven ;\
        ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

VOLUME /output

ADD compile.sh /compile.sh
CMD /compile.sh

#RUN apk --update --no-cache add bash curl linux-headers build-base autoconf automake libtool apr-util apr-util-dev openssl-devel

VOLUME /usr/local/autocomplete-curd
ADD target/autocomplete-crud-1.0.0-SNAPSHOT.jar /usr/local/autocomplete-curd/autocomplete-curd.jar
ADD key/autocomplete-demo.json /usr/local/autocomplete-curd/autocomplete-demo.json
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
RUN export GOOGLE_APPLICATION_CREDENTIALS="/usr/local/autocomplete-curd/autocomplete-demo.json"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/local/autocomplete-curd/autocomplete-curd.jar"]

