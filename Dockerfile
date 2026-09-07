FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY src/ .

RUN javac Main.java Task.java TaskManager.java

CMD ["java", "Main", "help"]