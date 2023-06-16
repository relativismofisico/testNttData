FROM openjdk:8-jdk-alpine
COPY "./target/inventory-0.0.1-SNAPSHOT.jar" "equipments.jar"
EXPOSE 8090
ENTRYPOINT [ "java","-jar","equipments.jar" ]