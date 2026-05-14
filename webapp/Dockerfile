FROM tomcat:10.1-jdk21

# デフォルトの Web アプリを削除
RUN rm -rf /usr/local/tomcat/webapps/*

# MySQL JDBC ドライバを Tomcat の lib に配置
ADD https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar \
    /usr/local/tomcat/lib/mysql-connector-j-8.3.0.jar

# アプリケーションは volume マウントで参照するため COPY しない

EXPOSE 8080
CMD ["catalina.sh", "run"]
