FROM node:latest AS mundia_node
# Définir le répertoire de travail dans le conteneur
WORKDIR /app
# Copiez package.json dans le répertoire de travail
COPY package.json ./
# Installer les dépendances
RUN npm install
COPY ./ /app/

RUN npm run build
# Utiliser Nginx comme serveur de production
FROM nginx:latest
# Copiez l'application React construite dans le
COPY --from=mundia_node /app/build /usr/share/nginx/html
# Exposer le port 80 pour le serveur Nginx
EXPOSE 80

# Démarrez Nginx lorsque le conteneur s'exécute
CMD ["nginx", "-g", "daemon off;"]