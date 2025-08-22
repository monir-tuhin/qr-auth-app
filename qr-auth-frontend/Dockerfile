### state 1: Build ###
FROM node:14.21.0-alpine AS builder
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production --aot --output-hashing=all



### stage 2: run stage ###
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
# Remove default nginx static assets
RUN rm -rf ./*
COPY --from=builder /app/dist/Angular11JwtAuth .
ENTRYPOINT ["nginx", "-g", "daemon off;"]
