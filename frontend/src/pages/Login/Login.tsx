import axios, { AxiosError } from "axios"; // Importe Axios e AxiosError
import { ErrorMessage, Field, Form, Formik, type FormikHelpers } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import * as Yup from "yup";
import LoginBackground from "../../assets/LoginBackground.png";
import { Button } from "../../components/Button/Button";
import { Input } from "../../components/Input/Input";
import { useAuth } from "../../hooks/useAuth";
import { loginUser, registerUser } from "../../services/authService";
import * as S from "./styles";

const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
const nameRegex = /^([A-Za-zÀ-ÖØ-öø-ÿ]+[-']?\s+){1,}[A-Za-zÀ-ÖØ-öø-ÿ]+[-']?$/;

export const Login = () => {
  const [isRegistering, setIsRegistering] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const initialValues = {
    name: "",
    email: "",
    cpf: "",
    password: "",
    confirmPassword: "",
    identifier: "",
  };

  const loginSchema = Yup.object({
    identifier: Yup.string()
      .required("Campo obrigatório")
      .test("emailOrCpf", "E-mail ou CPF inválido", (value) => {
        if (!value) return false;
        const isEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
        const isCpf = /^\d{11}$/.test(value.replace(/\D/g, ""));
        return isEmail || isCpf;
      }),
    password: Yup.string()
      .required("Campo obrigatório")
      .matches(
        passwordRegex,
        "A senha deve conter ao menos 8 caracteres, uma letra maiúscula, uma minúscula e um número",
      ),
  });

  const registerSchema = Yup.object({
    name: Yup.string()
      .required("Campo obrigatório")
      .matches(nameRegex, "Nome completo obrigatório"),
    email: Yup.string().required("Campo obrigatório").email("E-mail inválido"),
    cpf: Yup.string()
      .required("Campo obrigatório")
      .transform((value) => value.replace(/\D/g, ""))
      .matches(/^\d{11}$/, "CPF inválido"),
    password: Yup.string()
      .required("Campo obrigatório")
      .matches(
        passwordRegex,
        "A senha deve conter ao menos 8 caracteres, uma letra maiúscula, uma minúscula e um número",
      ),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref("password")], "As senhas não coincidem")
      .required("Campo obrigatório"),
  });

  const handleSubmit = async (
    values: typeof initialValues,
    formikHelpers: FormikHelpers<typeof initialValues>,
  ) => {
    try {
      if (isRegistering) {
        const { name, email, cpf, password } = values;
        await registerUser({ name, email, cpf, password });
        toast.success("Cadastro realizado!");
        formikHelpers.resetForm();
        setIsRegistering(false);
        return;
      }

      const { identifier, password } = values;
      const response = await loginUser({ identifier, password });

      const { user, token } = response;
      login(user, token);
      toast.success("Login realizado!");
      navigate("/dashboard");
    } catch (err) {
      let errorMessage = "Erro inesperado ao autenticar."; // Mensagem padrão de fallback

      // 1. Verifique se é um erro do Axios
      if (axios.isAxiosError(err)) {
        const axiosError = err as AxiosError; // Cast para AxiosError

        // 2. Verifique se o erro tem uma resposta do servidor
        if (axiosError.response) {
          // A estrutura de axiosError.response.data depende do seu backend (Spring Boot)
          // Cenário 1: String direta (como no seu User Controller: .body(e.getMessage()))
          if (typeof axiosError.response.data === "string") {
            errorMessage = axiosError.response.data;
          }
          // Cenário 2: Objeto com propriedade 'message'
          else if (
            typeof axiosError.response.data === "object" &&
            axiosError.response.data !== null &&
            "message" in axiosError.response.data &&
            typeof (axiosError.response.data as any).message === "string"
          ) {
            errorMessage = (axiosError.response.data as any).message;
          }
          // Cenário 3: Objeto com lista de 'errors' (erros de validação, ex: Spring's MethodArgumentNotValidException)
          else if (
            typeof axiosError.response.data === "object" &&
            axiosError.response.data !== null &&
            "errors" in axiosError.response.data &&
            Array.isArray((axiosError.response.data as any).errors)
          ) {
            const validationErrors = (axiosError.response.data as any).errors
              .map((error: any) => error.defaultMessage || error.message)
              .filter(Boolean); // Filtra mensagens vazias
            if (validationErrors.length > 0) {
              errorMessage = validationErrors.join(", "); // Junta as mensagens de erro
            } else if (
              "message" in axiosError.response.data &&
              typeof (axiosError.response.data as any).message === "string"
            ) {
              // Em alguns casos de validação, pode haver uma mensagem geral além dos campos específicos
              errorMessage = (axiosError.response.data as any).message;
            }
          }
          // Cenário 4: Outros formatos de erro que seu backend possa retornar (adapte conforme necessário)
          // Ex: { "detail": "Recurso não encontrado" }
          else if (
            typeof axiosError.response.data === "object" &&
            axiosError.response.data !== null &&
            "detail" in axiosError.response.data &&
            typeof (axiosError.response.data as any).detail === "string"
          ) {
            errorMessage = (axiosError.response.data as any).detail;
          }
        } else if (axiosError.request) {
          // A requisição foi feita, mas nenhuma resposta foi recebida (ex: rede offline, CORS bloqueado)
          errorMessage =
            "Nenhuma resposta recebida do servidor. Verifique sua conexão.";
        } else {
          errorMessage = axiosError.message; // Mensagem de erro do próprio Axios
        }
      } else if (err instanceof Error) {
        errorMessage = err.message;
      }

      toast.error(errorMessage);
    }
  };

  return (
    <S.LoginWrapper>
      <S.FormContainer>
        <Formik
          initialValues={initialValues}
          validationSchema={isRegistering ? registerSchema : loginSchema}
          onSubmit={handleSubmit}
        >
          {({ setFieldValue, values, resetForm }) => (
            <Form className="form-content">
              <h1>
                {isRegistering ? "Crie sua conta" : "Bem-vindo de volta!"}
              </h1>
              <p>
                {isRegistering
                  ? "Preencha os campos para criar sua conta"
                  : "Informe seus dados para acessar sua conta"}
              </p>

              {isRegistering && (
                <>
                  <div className="input-block">
                    <label>Nome</label>
                    <Field as={Input} name="name" placeholder="Seu nome" />
                    <ErrorMessage
                      name="name"
                      component="span"
                      className="error"
                    />
                  </div>

                  <div className="input-block">
                    <label>Email</label>
                    <Field
                      as={Input}
                      name="email"
                      placeholder="email@exemplo.com"
                    />
                    <ErrorMessage
                      name="email"
                      component="span"
                      className="error"
                    />
                  </div>

                  <div className="input-block">
                    <label>CPF</label>
                    <Input
                      name="cpf"
                      value={values.cpf
                        .replace(/\D/g, "")
                        .replace(/^(\d{3})(\d)/, "$1.$2")
                        .replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
                        .replace(/\.(\d{3})(\d)/, ".$1-$2")}
                      onChange={(e) => {
                        const raw = e.target.value.replace(/\D/g, "");
                        setFieldValue("cpf", raw);
                      }}
                      placeholder="000.000.000-00"
                      maxLength={14}
                    />
                    <ErrorMessage
                      name="cpf"
                      component="span"
                      className="error"
                    />
                  </div>
                </>
              )}

              {!isRegistering && (
                <div className="input-block">
                  <label>E-mail ou CPF</label>
                  <Field
                    as={Input}
                    name="identifier"
                    placeholder="Insira seus dados"
                  />
                  <ErrorMessage
                    name="identifier"
                    component="span"
                    className="error"
                  />
                </div>
              )}

              <div className="input-block">
                <label>Senha</label>
                <Field
                  as={Input}
                  name="password"
                  type="password"
                  placeholder="Digite sua senha"
                />
                <ErrorMessage
                  name="password"
                  component="span"
                  className="error"
                />
              </div>

              {isRegistering && (
                <div className="input-block">
                  <label>Confirme a senha</label>
                  <Field
                    as={Input}
                    name="confirmPassword"
                    type="password"
                    placeholder="Confirme sua senha"
                  />
                  <ErrorMessage
                    name="confirmPassword"
                    component="span"
                    className="error"
                  />
                </div>
              )}

              <Button type="submit">
                {isRegistering ? "Cadastrar" : "Entrar"}
              </Button>

              <div className="divider">
                <hr />
                <span>ou</span>
              </div>

              <p className="signup-text">
                {isRegistering ? "Já tem uma conta?" : "Não tem uma conta?"}
                <a
                  onClick={() => {
                    resetForm();
                    setIsRegistering(!isRegistering);
                  }}
                >
                  {isRegistering ? " Entrar" : " Criar conta"}
                </a>
              </p>
            </Form>
          )}
        </Formik>
      </S.FormContainer>
      <S.ImageContainer background={LoginBackground} />
    </S.LoginWrapper>
  );
};
