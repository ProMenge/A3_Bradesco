# Etapa 1: build
FROM node:18 AS builder
WORKDIR /app
COPY . .
COPY .env.production .env
RUN npm install
RUN npm run build

# Etapa 2: servidor
FROM node:18-slim
WORKDIR /app
RUN npm install -g serve
COPY --from=builder /app/dist ./dist
EXPOSE 80
CMD ["serve", "-s", "dist", "-l", "80"]
