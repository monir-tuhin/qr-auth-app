#if docker daemon throwing permission denied to stop or remove container or images
#sudo systemctl restart docker.socket docker.service

#docker stop $(docker ps -q)
#docker rm $(docker ps -a -q)
#docker rmi $(docker images -q)
#docker-compose down
#docker-compose build
#docker-compose up

#docker-compose stop; # stop all container of docker compose
#docker-compose rm; # remove all container of docker compose

docker-compose down --rmi all # stop and remove containers and images
git pull origin main
COMPOSE_BAKE=true docker-compose up -d --build

