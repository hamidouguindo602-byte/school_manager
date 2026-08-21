# ---------- Etape 1 : build ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
# URL relative : nginx proxifie /api vers le backend (voir nginx.conf)
ENV VITE_API_BASE_URL=/api
RUN npm run build

# ---------- Etape 2 : runtime (nginx) ----------
FROM nginx:1.27-alpine AS runtime

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
