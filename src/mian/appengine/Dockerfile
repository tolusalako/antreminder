FROM gcr.io/google_appengine/openjdk8
VOLUME /tmp
ENV JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=128M
ADD managed-vms-spark-1.0-jar-with-dependencies.jar
RUN git pull https://github.com/tolusalako/antreminder
RUN cd antreminder
RUN gradle build bootRun

#CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]