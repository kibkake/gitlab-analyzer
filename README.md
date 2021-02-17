## GitLab Analyzer
  
  
### A program that chooses a timeline snapshot from a GitLab Repository and fetches all activities of members from that timeline and produces further analysis on the contributions of members.
  
## Instructions:
  
### Production JAR Executable:
### To run the fat JAR file with React and Spring Boot Java incorporated, use the following commands or execute the batch file - `run.bat`
###    In the root directory in cmd or powershell: 
###        `gradle clean build`
###        `java -jar Java/build/libs/Java-0.0.2-SNAPSHOT.jar`
###  
### Spring Boot App:
### The easiest way to run the program is to open up `cmd`, navigate to `/Java` folder and run `gradle bootRun` or `grunner`
  
### You may choose to run this with IntelliJ as well but you must add the necessary libraries:
  
+ org.mongodb:mongo-java-driver:3.12.7
+ org.gitlab:java-gitlab-api:4.0.0
+ org.springframework.boot:spring-boot-starter-web:2.2.0.RELEASE
+ org.projectlombok:lombok:1.18.16
+ org.springframework.boot:spring-boot-starter-test
+ com.google.code.gson:gson:2.8.5

### React UI:
### React.js is part of Node.js so you will need to install the package manager `npm` first
### Once you have npm, navigate to the `/reactui` folder and type `npm install`, then `npm start` 

## Set the src folder as your root
  
## **[GitLab Test Server](https://cmpt373-1211-10.cmpt.sfu.ca/)**