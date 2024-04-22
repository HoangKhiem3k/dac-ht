FROM openjdk:11

ARG USER_HOME_DIR="/root"
ARG MAVEN_URL=https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

#install maven
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_URL}
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Create and change to the app directory.
WORKDIR /app/backend

# Copy local code to the container image.
COPY . ./

#run build
RUN mvn clean install

# run app
CMD ["/usr/bin/java", "-jar", "./target/ht-backend-0.0.1.jar"]
