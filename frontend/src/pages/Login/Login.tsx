import { ErrorMessage, Field, Form, Formik } from "formik";
import { useState } from "react";
import * as Yup from "yup";
import LoginBackground from "../../assets/LoginBackground.png";
import { Button } from "../../components/Button/Button";
import { Input } from "../../components/Input/Input";
import * as S from "./styles";

export const Login = () => {
  const [isRegistering, setIsRegistering] = useState(false);

  const loginSchema = Yup.object().shape({
    identifier: Yup.string().required("Campo obrigatório"),
    password: Yup.string().required("Campo obrigatório"),
  });

  const registerSchema = Yup.object().shape({
    name: Yup.string().required("Campo obrigatório"),
    identifier: Yup.string()
      .email("E-mail inválido")
      .required("Campo obrigatório"),
    password: Yup.string()
      .min(6, "A senha deve ter no mínimo 6 caracteres")
      .required("Campo obrigatório"),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref("password")], "As senhas não coincidem")
      .required("Campo obrigatório"),
  });

  const initialValues = {
    name: "",
    identifier: "",
    password: "",
    confirmPassword: "",
  };

  const handleSubmit = (values: typeof initialValues) => {
    if (isRegistering) {
      console.log("Cadastrando:", values);
    } else {
      console.log("Entrando:", values);
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
          {() => (
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
                <div className="input-block">
                  <label htmlFor="name">Nome</label>
                  <Field as={Input} name="name" placeholder="Insira seu nome" />
                  <ErrorMessage
                    name="name"
                    component="span"
                    className="error"
                  />
                </div>
              )}

              <div className="input-block">
                <label htmlFor="identifier">E-mail ou CPF</label>
                <Field
                  as={Input}
                  name="identifier"
                  placeholder="Insira seu e-mail ou CPF"
                />
                <ErrorMessage
                  name="identifier"
                  component="span"
                  className="error"
                />
              </div>

              <div className="input-block">
                <div className="label-wrapper">
                  <label htmlFor="password">Senha</label>
                  {!isRegistering && <a href="#">Esqueceu a senha?</a>}
                </div>
                <Field
                  as={Input}
                  name="password"
                  type="password"
                  placeholder="Insira sua senha"
                />
                <ErrorMessage
                  name="password"
                  component="span"
                  className="error"
                />
              </div>

              {isRegistering && (
                <div className="input-block">
                  <label htmlFor="confirmPassword">Confirme a senha</label>
                  <Field
                    as={Input}
                    name="confirmPassword"
                    type="password"
                    placeholder="Insira novamente sua senha"
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
                <a onClick={() => setIsRegistering(!isRegistering)}>
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
