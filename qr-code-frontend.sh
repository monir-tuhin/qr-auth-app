docker-compose kill frontend
docker-compose rm -f frontend
docker rmi -f monirzaman/qr-code-scan-frontend
docker-compose up -d --build frontend
