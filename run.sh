#!/bin/sh
PROFILE=$1
CURRENT_DIRECTORY="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JAVA_OPTS="${JAVA_OPTS} -Xms1G -Xmx1G"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:MaxGCPauseMillis=30"
JAVA_OPTS="${JAVA_OPTS} -Xloggc:${CURRENT_DIRECTORY}/logs/GC.log"
JAVA_OPTS="${JAVA_OPTS} -verbose:gc -XX:+PrintGCTimeStamps -XX:+PrintGCDetails"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M"
JAVA_OPTS="${JAVA_OPTS} -Dsun.net.inetaddr.ttl=0"
JAVA_OPTS="${JAVA_OPTS} -Duser.timezone=Asia/Seoul"
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=$PROFILE"
AG_PID=`ps -ef | grep ag_assessment.jar | grep -v grep | awk '{print $2}'`

#echo $JAVA_OPTS

if [ ! -z "$AG_PID" ]; then
    echo "Shutdown process. pid : [$AG_PID]"
    kill -9 $AG_PID
    sleep 10
fi

echo "Ag_Assessment process is starting."

java -server $JAVA_OPTS -jar $CURRENT_DIRECTORY/ag_assessment.jar > $CURRENT_DIRECTORY/logs/nohup.log &
sleep 15

AG_PID=`ps -ef | grep ag_assessment.jar | grep -v grep | awk '{print $2}'`
if [ -z "$AG_PID" ]; then
    echo "Failed to start Ag_Assessment Application process."
    exit 1
else
    echo "Ag_Assessment process starting is done."
fi