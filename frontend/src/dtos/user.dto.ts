export interface CreateUserDTO {
  name: string;
  cpf: string;
  email: string;
  password: string;
}

export interface LoginDTO {
  identifier: string;
  password: string;
}
