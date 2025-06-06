import type { CreateUserDTO, LoginDTO } from "../dtos/user.dto";
import { api } from "./api";

export const loginUser = async (data: LoginDTO) => {
  console.log("Enviando dados para o backend:", data)
  const response = await api.post("/users/login", data);
  return response.data;
};

export const registerUser = async (user: CreateUserDTO) => {
  console.log("Enviando dados cadastrais para o backend:", user)
  const response = await api.post("/users", user);
  return response.data;
};
