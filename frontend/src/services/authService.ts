import { api } from "./api";

interface RegisterPayload {
  name: string;
  identifier: string; // email ou CPF
  password: string;
}

interface LoginPayload {
  identifier: string;
  password: string;
}

export const loginUser = async ({ identifier, password }: LoginPayload) => {
  const res = await api.get("/users");
  const users = res.data;

  const user = users.find((u: any) => {
    return (
      (u.email === identifier || u.cpf === identifier) &&
      u.password === password
    );
  });

  if (!user) {
    throw new Error("Credenciais inválidas");
  }

  return user;
};

export const registerUser = async ({ name, identifier, password }: RegisterPayload) => {
  const res = await api.get("/users");
  const users = res.data;

  const alreadyExists = users.some((u: any) => {
    return u.email === identifier || u.cpf === identifier;
  });

  if (alreadyExists) {
    throw new Error("Já existe um usuário com este e-mail ou CPF");
  }

  const isCpf = /^\d{11}$/.test(identifier);

  const newUser = {
    name,
    cpf: isCpf ? identifier : "",
    email: isCpf ? "" : identifier,
    password,
  };

  const created = await api.post("/users", newUser);
  return created.data;
};
