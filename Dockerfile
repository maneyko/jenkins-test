FROM ruby:3.0-rc

WORKDIR /
COPY .env.docker /
RUN . /.env.docker
