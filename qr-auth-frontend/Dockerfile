### state 1: Build ###
FROM node:14.21.0-alpine AS builder
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install -g npm@8 && npm install
COPY . .
RUN npm run-script build-prod



### stage 2: run stage ###
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
# Remove default nginx static assets
RUN rm -rf ./*
COPY --from=builder /app/dist/qr-auth-frontend .
ENTRYPOINT ["nginx", "-g", "daemon off;"]
