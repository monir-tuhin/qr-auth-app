mvn clean install -Pprod -DskipTests
#docker rm -f tour-event-qa-v1
#docker rmi -f tour_event_qa
#
#docker build -f Dockerfile -t tour_event_qa .
#
#docker run --name tour-event-qa-v1 -e 'spring.profiles.active=qa' -e 'TZ=Asia/Dhaka' -d -p 8066:8078 tour_event_qa
