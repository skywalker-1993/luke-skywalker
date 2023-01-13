FROM gradle:jdk11-alpine
ARG BUILD_MODE
ENV BUILD_MODE=${BUILD_MODE}
WORKDIR /home/test_repo/
COPY . /home/test_repo/
CMD gradle clean build