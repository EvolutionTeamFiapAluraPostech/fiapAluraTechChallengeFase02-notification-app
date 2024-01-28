FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/ParkingNotification-*-SNAPSHOT.jar
COPY ${JAR_FILE} parkingNotification.jar
ENTRYPOINT ["java", "-jar", "/parkingNotification.jar"]
