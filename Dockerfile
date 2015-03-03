FROM dockerfile/java:oracle-java8
MAINTAINER marcel@hightml.com
EXPOSE 8000



ENV MAVEN_VERSION 3.2.5

RUN curl -sSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

RUN mvn install



ADD target/scanman-1.0-SNAPSHOT.jar /data/scanman-1.0-SNAPSHOT.jar

CMD java -jar scanman-1.0-SNAPSHOT.jar
