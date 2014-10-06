FROM sionsmith/activator

MAINTAINER Sion Smith sion.smith@gmail.com

# make directories
RUN mkdir /rest-endpoint-src /rest-endpoint-prod /logs

# add volumes.
VOLUME [ "/logs" ]

ADD . /rest-endpoint-src

#build project for production
RUN cd /rest-endpoint-src && activator dist

RUN mv /rest-endpoint-src/target/universal/rest_endpoint-1.0-SNAPSHOT.zip /rest-endpoint-prod/rest_endpoint.zip
RUN unzip /rest-endpoint-prod/rest_endpoint.zip -d /rest-endpoint-prod

# Expose the http port
EXPOSE 9000

# Run the app
CMD ["/rest-endpoint-prod/rest_endpoint-1.0-SNAPSHOT/bin/rest_endpoint"]