FROM openjdk:11

COPY ./target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar spring-boot_security-demo-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","spring-boot_security-demo-0.0.1-SNAPSHOT.jar"]

#https://www.javainuse.com/devOps/docker/docker-mysql