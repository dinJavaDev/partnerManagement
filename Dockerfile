FROM openjdk:8
ADD target/partnerManagementApp-1.0.jar partnerManagementApp-1.0.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "partnerManagementApp-1.0.jar"]