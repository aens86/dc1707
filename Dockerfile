FROM  ubuntu:18.04
RUN apt update
RUN apt install default-jdk maven git openssh-client docker.io -y
WORKDIR /home/jenkins/.ssh/
COPY  id_rsa /home/jenkins/.ssh/id_rsa
RUN chmod 600 /home/jenkins/.ssh/id_rsa
RUN groupadd -g 109 jenkins && \
    useradd -u 109 -g jenkins -m -s /bin/bash jenkins
RUN chown -R jenkins:jenkins /home/jenkins


#WORKDIR /home/user
#RUN wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.45/bin/apache-tomcat-9.0.45.tar.gz
#RUN tar -zxvf apache-tomcat-9.0.45.tar.gz
#RUN mv apache-tomcat-9.0.45 /opt/tomcat
#ENV CATALINA_HOME /opt/tomcat
#ENV PATH $CATALINA_HOME/bin:$PATH
#EXPOSE 8085
#RUN git clone https://github.com/boxfuse/boxfuse-sample-java-war-hello.git /home/user/1

#WORKDIR /home/user/1
#RUN mvn package 
#RUN cp /home/user/1/target/hello-1.0.war /opt/tomcat/webapps/
#CMD ["/opt/tomcat/bin/catalina.sh", "run"]
