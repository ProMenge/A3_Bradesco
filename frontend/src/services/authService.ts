import type { CreateUserDTO, LoginDTO } from "../dtos/user.dto";
import { api } from "./api";

export const loginUser = async (data: LoginDTO) => {
  const response = await api.post("/users/login", data);
  return response.data;
};

export const registerUser = async (user: CreateUserDTO) => {
  const response = await api.post("/users", user);
  return response.data;
};
