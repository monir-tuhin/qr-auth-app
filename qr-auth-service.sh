docker-compose kill backend
docker-compose rm -f backend
docker rmi -f monirzaman/qr-auth-service
docker-compose up -d --build backend
