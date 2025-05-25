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
    identifier: Yup.string().required("Required"),
    password: Yup.string().required("Required"),
  });

  const registerSchema = Yup.object().shape({
    name: Yup.string().required("Required"),
    identifier: Yup.string().email("Invalid email").required("Required"),
    password: Yup.string().min(6, "Min 6 characters").required("Required"),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref("password")], "Passwords do not match")
      .required("Required"),
  });

  const initialValues = {
    name: "",
    identifier: "",
    password: "",
    confirmPassword: "",
  };

  const handleSubmit = (values: typeof initialValues) => {
    if (isRegistering) {
      console.log("Registering:", values);
    } else {
      console.log("Logging in:", values);
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
              <h1>{isRegistering ? "Get Started Now" : "Welcome Back!"}</h1>
              <p>
                {isRegistering
                  ? "Create your account to start using the platform"
                  : "Enter your Credentials to access your account"}
              </p>

              {isRegistering && (
                <div className="input-block">
                  <label htmlFor="name">Name</label>
                  <Field as={Input} name="name" placeholder="Enter your name" />
                  <ErrorMessage
                    name="name"
                    component="span"
                    className="error"
                  />
                </div>
              )}

              <div className="input-block">
                <label htmlFor="identifier">Email address or CPF</label>
                <Field
                  as={Input}
                  name="identifier"
                  placeholder="Enter your email or CPF"
                />
                <ErrorMessage
                  name="identifier"
                  component="span"
                  className="error"
                />
              </div>

              <div className="input-block">
                <div className="label-wrapper">
                  <label htmlFor="password">Password</label>
                  {!isRegistering && <a href="#">Forgot password?</a>}
                </div>
                <Field
                  as={Input}
                  name="password"
                  type="password"
                  placeholder="Enter your password"
                />
                <ErrorMessage
                  name="password"
                  component="span"
                  className="error"
                />
              </div>

              {isRegistering && (
                <div className="input-block">
                  <label htmlFor="confirmPassword">Confirm Password</label>
                  <Field
                    as={Input}
                    name="confirmPassword"
                    type="password"
                    placeholder="Re-enter your password"
                  />
                  <ErrorMessage
                    name="confirmPassword"
                    component="span"
                    className="error"
                  />
                </div>
              )}

              <Button type="submit">
                {isRegistering ? "Signup" : "Sign In"}
              </Button>

              <div className="divider">
                <hr />
                <span>Or</span>
              </div>

              <p className="signup-text">
                {isRegistering
                  ? "Have an account? "
                  : "Don’t have an account? "}
                <a onClick={() => setIsRegistering(!isRegistering)}>
                  {isRegistering ? "Sign In" : "Sign Up"}
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
