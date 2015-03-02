FROM dockerfile/java:oracle-java7
MAINTAINER marcel@hightml.com
EXPOSE 8000
CMD java -jar scanman-1.0-SNAPSHOT.jar
ADD target/scanman-1.0-SNAPSHOT.jar /data/scanman-1.0-SNAPSHOT.jar
