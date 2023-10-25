FROM gradle:8.4.0-jdk11-alpine
ARG TEST_ENV
ENV TEST_ENV=${TEST_ENV}
WORKDIR /home/test_repo/
COPY . /home/test_repo/
CMD gradle clean build -Ptest_mode=${TEST_ENV} -Dkarate.env=${TEST_ENV}