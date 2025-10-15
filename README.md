## Como rodar separadamente
```bash
# Abrir um shell no container do backend
docker exec -it spring-backend sh

# Abrir um shell no container do frontend
docker exec -it angular-frontend sh

# Remover todos os containers, imagens, volumes e redes não utilizados
docker system prune -a --volumes

# Parar e remover todos os containers
docker rm -f $(docker ps -aq)
```