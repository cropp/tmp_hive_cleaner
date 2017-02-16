# Hive Temp File Cleanup Tool

This tool will go through hdfs://tmp/hive/\<userDir> and remove any directories that are older than the default 3 days.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

* Java 8
* Newer Maven 

### Installing

mvn clean package 

## Deploying 

hadoop jar target/cleaner-job.jar CleanHiveTmp

## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) - IDE
* [Maven](https://maven.apache.org/) - Dependency Management
 
## Authors

* **Curtis Ropp** - *Initial work* - [TeamRopp](http://www.teamropp.com/)


