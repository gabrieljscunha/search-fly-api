FROM debian:9.4

RUN apt-get update \
    && apt-get install -y \
    libusb-1.0-0 \
    procps \
    telnet \
    vim \
    wget

RUN mkdir -p /opt/java/versions

# to x64 system
RUN wget https://cdn.azul.com/zulu/bin/zulu8.36.0.1-ca-jdk8.0.202-linux_x64.tar.gz -P /opt/java/versions
RUN cd /opt/java/versions; tar -xf zulu8.36.0.1-ca-jdk8.0.202-linux_x64.tar.gz
RUN ln -s /opt/java/versions/zulu8.36.0.1-ca-jdk8.0.202-linux_x64 /opt/java/jvm
RUN apt-get -y install maven

RUN mkdir /opt/search-fly-api 
ADD target/search-fly-api-1.0.1-SNAPSHOT-exec.jar /opt/search-fly-api/
ADD conf /opt/search-fly-api/conf
WORKDIR /opt/search-fly-api

CMD java -jar /opt/search-fly-api/search-fly-api-1.0.1-SNAPSHOT-exec.jar --spring.config.location=file:///opt/search-fly-api/conf/application.properties

