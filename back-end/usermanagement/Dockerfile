FROM gradle:6.7.1-jdk8 AS build
COPY --chown=gradle:gradle . /apps
###RUN mkdir -p /apps###
WORKDIR /apps
COPY build.gradle /apps
COPY src /apps/src
RUN gradle build

FROM openjdk:8-jre-slim
COPY --from=build /apps/build/libs/*.jar app.jar
EXPOSE 9082
ENTRYPOINT ["java","-jar","app.jar"]
