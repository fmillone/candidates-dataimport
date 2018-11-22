FROM java:8

EXPOSE 8081

COPY build/libs/candidates-data-import.jar /app.jar

ENV ARGS=""

ENTRYPOINT [ "sh", "-c", "java $ARGS -jar /app.jar " ]

