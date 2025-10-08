# Dockerfile.dev
FROM node:20-alpine

WORKDIR /app

# Instala Angular CLI
RUN npm install -g @angular/cli@17

# Copia package.json e instala dependências
COPY package*.json ./
RUN npm install

# Copia todo o código
COPY . .

# Expõe porta do ng serve
EXPOSE 4200

# Comando para desenvolvimento
CMD ["ng", "serve", "--host", "0.0.0.0"]
