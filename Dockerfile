# Use a base image with Java and Alpine
FROM openjdk:19-alpine

# Set the working directory inside the container
WORKDIR /app

# Set the environment variable for the server port
ENV SERVER_PORT=8484

# Install wget utility for downloading files
RUN apk update && apk add --no-cache wget

# Set the desired version of your backend JAR file
ARG JAR_VERSION=v1.0.0

# Set the download URL for the JAR file
ARG JAR_DOWNLOAD_URL=https://github.com/sliderzxc/EveryoneBotTelegram/releases/download/${JAR_VERSION}/application.jar

# Download the JAR file from the specified URL
RUN wget --quiet --show-progress --no-cache --progress=bar: ${JAR_DOWNLOAD_URL} -O application.jar

# Expose the port on which your application will run
EXPOSE $SERVER_PORT

# Set the command to run the application
# Refer to the documentation on what environment variables should be set to run the application
CMD ["java", "-jar", "application.jar"]
