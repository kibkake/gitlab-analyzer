# GitLab Analyzer
  
  
## A program that chooses a timeline snapshot from a GitLab Repository and fetches all activities of members from that timeline and produces further analysis on the contributions of members.
  
## Instructions:
### The easiest way to run the program is to open up `cmd`, navigate to `/Java` folder and run `gradle bootRun` or `grunner`
  
### You may choose to run this with IntelliJ as well but you must add the necessary libraries:
  
+ org.mongodb:mongo-java-driver:3.12.7
+ org.gitlab:java-gitlab-api:4.0.0
+ org.springframework.boot:spring-boot-starter-web:2.2.0.RELEASE
+ org.projectlombok:lombok:1.18.16
+ org.springframework.boot:spring-boot-starter-test
+ com.google.code.gson:gson:2.8.5
  
## **[GitLab Test Server](https://cmpt373-1211-10.cmpt.sfu.ca/)**