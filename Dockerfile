FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build WAR file
RUN mvn -B clean package -DskipTests

FROM tomcat:10.1-jdk17-temurin

ENV CATALINA_HOME=/usr/local/tomcat

WORKDIR /usr/local/tomcat

# Remove default Tomcat applications
RUN rm -rf webapps/*

# Configure Tomcat to use Render's PORT
RUN sed -i 's/port="8080"/port="${tomcat.http.port}" address="0.0.0.0"/' conf/server.xml

# Deploy application as ROOT
COPY --from=build /app/target/ShoppingCartApp.war webapps/ROOT.war

# Render default port
EXPOSE 10000

# Start Tomcat using Render's PORT
CMD ["sh", "-c", "CATALINA_OPTS=\"${CATALINA_OPTS:-} -Dtomcat.http.port=${PORT:-10000}\" exec catalina.sh run"]
