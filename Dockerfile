FROM eclipse-temurin:23-jdk
RUN apt-get update && apt-get install -y \
    libxrender1 libxtst6 libxi6 libfreetype6 libxext6 libxrandr2 libxxf86vm1 libgtk-3-0 libgl1\
    && apt-get clean

ENV DISPLAY=:0
WORKDIR /app
COPY . /app

CMD ["./gradlew","run"]