# Project Readme

## Project Architecture

This project is a Java-based application designed to collect and send system data. It uses a modular architecture with separate components for each type of data it collects. The main components are:

- `CpuUsage`: This component is responsible for collecting CPU usage data.
- `InternetSpeed`: This component is responsible for collecting internet speed data.
- `RamUsage`: This component is responsible for collecting RAM usage data.
- `Storage`: This component is responsible for collecting storage data.

These components are orchestrated by the `CollectData` class, which starts the monitoring process. The `Connection` class is then used to send this data.

## Prerequisites

To run this project, you will need:

- Java Development Kit (JDK)
- Maven
- Docker

## How to Run

### Building the Project

First, you need to build the project using Maven. Navigate to the project directory and run the following command:

```bash
mvn clean install
```
This will create a JAR file in the `out/artifacts/DataGatherer_jar/` directory.

### Building the Docker Image

The project includes a Dockerfile for building a Docker image of the application. To build the image, run the following command in the project directory:

```
docker build -t datagatherer .
```
This will create a Docker image named `datagatherer`.

### Running the Docker Container

To run the application in a Docker container, use the following command:
```
docker run --name serverside -p 6789:6789 datagatherer
```
This will start the application and expose it on port 6789.
