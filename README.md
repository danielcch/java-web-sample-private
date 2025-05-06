# java-web-sample

# Build jar
mvn clean package

# Build image
docker build -t java-web-sample .

# Run docker
docker run --rm -p 8080:8080 java-web-sample
