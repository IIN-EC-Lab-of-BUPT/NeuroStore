FROM centos:latest
MAINTAINER cy "chenyang2718@163.com"
WORKDIR /opt/java
ADD jre-8u341-linux-x64.tar.gz  /opt/java
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/time \
RUN yum -y install kde-l10n-Chinese glibc-common \
RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
ENV TZ=Asia/Shanghai
ENV LC_ALL zh_CN.utf8
ENV JAVA_HOME /opt/java/jre1.8.0_341
ENV PATH $JAVA_HOME/bin:$PATH
WORKDIR /opt
ADD ./DataManager /opt/DataManager 
ADD ./DataPersistent-1.2.jar /opt/
EXPOSE 8081

ENTRYPOINT [ "java" ,"-Dspring.config.location=/opt/DataManager/config/application.properties", "-jar" ]
CMD ["/opt/DataPersistent-1.2.jar"]


