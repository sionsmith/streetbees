#!/bin/bash


echo "Starting rest endpoint"
java ${ALL_OPTIONS} -jar /admin-console/rest-endpoint-server-allinone.jar ${ARGS}


***************
#!/bin/bash

# Ansible defines the following variables:
# GRAPHITE: Graphite server
# ZK_LIST: Fully defined <zk1>:2181,<zk2>:2181,... string

echo "ZOOKEEPER_HOSTS=${ZK_LIST}"
sed -i "s|{{ZOOKEEPER_HOSTS}}|${ZK_LIST}|g" /rest_endpoint/config/restserver.prod.properties

JVM_FLAGS="-server -XX:+UseCompressedOops"
JVM_GC_OPTS="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC -XX:+CMSClassUnloadingEnabled -XX:+CMSScavengeBeforeRemark"
JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=30200 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
# Logback finds its stuff from here
CLASSPATH="/rest_endpoint/config"
ALL_OPTIONS="${JVM_FLAGS} ${JVM_GC_OPTS} ${JMX_OPTS} -cp $CLASSPATH"

ARGS="--port 8080"
if [ "x$GRAPHITE" != "x" ]; then
	ARGS+=" --graphite ${GRAPHITE}"
fi

echo "Starting rest endpoint"
java ${ALL_OPTIONS} -jar /rest_endpoint/rest-endpoint-server-allinone.jar ${ARGS}
