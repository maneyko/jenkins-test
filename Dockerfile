FROM ruby:3.0-rc

COPY .env.docker .env.docker
RUN source .env.docker
