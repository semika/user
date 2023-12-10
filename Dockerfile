FROM openjdk:8
ARG JAR_FILE=target/user-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

#Expose ports from docker container to outside.
EXPOSE 9005
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

#docker run -p 8989:8989 -m 8000m -e JAVA_OPTIONS='-Xms2048m -Xmx4096m' -e ENV_OPTS='--env.type=prod' portal_rule

#--publish asks Docker to forward traffic incoming on the host’s port 8000, to the container’s port 8080 (containers have their own private set of ports, 
# so if we want to reach one from the network, we have to forward traffic to it in this way; 
# otherwise, firewall rules will prevent all network traffic from reaching your container, as a default security posture).
#--detach asks Docker to run this container in the background.
#--name lets us specify a name with which we can refer to our container in subsequent commands, in this case bb.
