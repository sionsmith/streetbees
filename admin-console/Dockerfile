FROM sionsmith/oracle-java8

MAINTAINER Sion Smith sion.smith@gmail.com

RUN apt-get update; apt-get install -y gzip wget git maven

# make directories
RUN mkdir /admin-console /logs /admin-console/config

# add volumes.
VOLUME [ "/logs" ]
VOLUME [ "/admin-console/config" ]

# add server start script need to update props with kafka ips
ADD config/start_server.sh /admin-console/start_server.sh
RUN chmod +x /admin-console/start_server.sh

# Expose the http port
EXPOSE 8080

# Run the app
CMD ["/admin-console/start_server.sh"]