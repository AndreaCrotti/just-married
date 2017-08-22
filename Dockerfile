FROM clojure:lein-2.7.1
# FROM java:8-alpine

ADD target/uberjar/just-married.jar /just-married/app.jar

WORKDIR /just-married
EXPOSE 8080

CMD ["java", "-jar", "/just-married/app.jar"]
