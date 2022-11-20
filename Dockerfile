FROM adoptopenjdk:11-jre-hotspot
COPY ./build/libs/testTask-0.0.1.jar testTask-0.0.1.jar
ENTRYPOINT ["java","-jar","/testTask-0.0.1.jar"]