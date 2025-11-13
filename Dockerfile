# Usa imagem oficial do Tomcat com Java 17
FROM tomcat:9.0-jdk17

# Copia o .war gerado pelo Maven para o Tomcat
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080 (Render usa essa por padrão)
EXPOSE 8080

# Comando de inicialização do Tomcat
CMD ["catalina.sh", "run"]
