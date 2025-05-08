# java-web-sample

# Build jar
mvn clean package

# Build with docker
docker run -it --rm -v .:/usr/src/javawebsample -v $HOME/.m2:/root/.m2 -w /usr/src/javawebsample maven:3.8.6-openjdk-11 mvn clean install

# Build image
docker build -t java-web-sample .

# Run docker
docker run --rm -p 8080:8080 java-web-sample
