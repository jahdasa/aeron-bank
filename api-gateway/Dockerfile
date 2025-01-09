ARG REPO_NAME=docker.mofid.dev/docker.io/
ARG IMAGE_NAME=azul/zulu-openjdk-debian
ARG IMAGE_TAG=21
FROM ${REPO_NAME}${IMAGE_NAME}:${IMAGE_TAG}

SHELL [ "/bin/bash", "-o", "pipefail", "-c" ]

# Create app user and group
RUN groupadd -r app && useradd --no-log-init -r -g app app

# Create home directory for aeron
RUN mkdir /home/app && chown app:app /home/app
RUN mkdir /home/app/jar && chown app:app /home/app/jar

WORKDIR /home/app/jar/
COPY --chown=app:app --chmod=755 ./build/libs/api-gateway.jar /home/app/jar/api-gateway.jar

# Set the user to aeron
USER app

CMD ["java", "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED", "-Djava.net.preferIPv4Stack=true", "-jar","/home/app/jar/api-gateway.jar"]
