# Dockerfile.dev
FROM node:20-alpine

RUN npm config set prefix /root/.npm-global
ENV PATH=/root/.npm-global/bin:$PATH

# Instala Angular CLI
RUN npm install -g @angular/cli@17

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 4200

# Comando para desenvolvimento
CMD ["ng", "serve", "--host", "0.0.0.0"]
