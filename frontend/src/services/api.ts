import axios from "axios";
import { toast } from "react-toastify";

// 1. Cria a instância base da API
export const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

// 2. Interceptador de requisição
api.interceptors.request.use(
  (config) => {
    // Recupera o token salvo (se houver)
    const token = localStorage.getItem("token");

    const isLoginRoute = config.url?.includes("/users/login");

    if (token && !isLoginRoute) {
      // Adiciona o token no header Authorization
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    // Caso ocorra erro antes da requisição sair
    return Promise.reject(error);
  },
);

// 3. Interceptador de resposta
api.interceptors.response.use(
  (response) => {
    // Resposta normal: passa direto
    return response;
  },
  (error) => {
    // Resposta com erro: analisa o código
    const status = error.response?.status;

    if (status === 403) {
      toast.error("Você não tem permissão para essa ação.");
    }

    if (status === 500) {
      toast.error("Erro interno no servidor.");
    }

    // Retorna o erro para o .catch de quem chamou a API
    return Promise.reject(error);
  },
);
