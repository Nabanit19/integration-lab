FROM eclipse-temurin:21-jre
WORKDIR /app
#
## Run as a non-root user
#RUN useradd --system --no-create-home appuser
#USER appuser

# Copy the pre-built jar
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]