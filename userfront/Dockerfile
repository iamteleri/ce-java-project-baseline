#stage 1
FROM node:18.14.2-alpine as node
WORKDIR /app
COPY . .
RUN npm install -g npm@9.6.1
RUN npm run build
# #stage 2
FROM nginx:alpine
COPY --from=node /app/dist/userfront /usr/share/nginx/html