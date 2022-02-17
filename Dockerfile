FROM amazoncorretto:11-alpine-jdk
MAINTAINER dizsart
COPY target/TextFileComparator-0.0.1-SNAPSHOT.jar text-file-comparator-0.0.1.jar
ENTRYPOINT ["java","-jar","/text-file-comparator-0.0.1.jar"]