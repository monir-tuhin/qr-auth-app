docker-compose kill backend
docker-compose rm -f backend
docker rmi -f monirzaman/qr-code-scan-backend
docker-compose up -d --build backend
