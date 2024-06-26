# Start with a base image containing Java runtime
FROM amazoncorretto:21

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 6789

# The application's jar file
ARG JAR_FILE=out/artifacts/DataGatherer_jar/DataGatherer.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]