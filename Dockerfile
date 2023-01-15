FROM gradle:jdk11-alpine
ARG TEST_MODE
ENV TEST_MODE=${TEST_MODE}
WORKDIR /home/test_repo/
COPY . /home/test_repo/
CMD gradle clean build -Ptest_mode=${TEST_MODE}