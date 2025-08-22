docker-compose kill frontend
docker-compose rm -f frontend
docker rmi -f monirzaman/qr-auth-frontend
docker-compose up -d --build frontend
