import styled from "styled-components";
interface ImageContainerProps {
  background: string;
}
export const LoginWrapper = styled.div`
  display: flex;
  height: 100vh;
  width: 100vw;
`;

export const FormContainer = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f5f5;

  form {
    display: flex;
    flex-direction: column;
    gap: 1.25rem;
    width: 100%;
    max-width: 400px;
  }

  h1 {
    font-size: 36px;
    font-weight: 700;
    margin-bottom: 0.5rem;
    color: #111;
    padding-bottom: 8px;
  }

  p {
    font-size: 18px;
    color: #444;
    margin-bottom: 60px;
  }

  a {
    font-size: 14px;
    color: #4a90e2;
    text-decoration: none;

    align-self: flex-end;
  }

  a:hover {
    text-decoration: underline;
  }

  /* Wrapper interno para título + campos */
  .form-content {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: stretch;
  }

  label {
    font-size: 20px;
    font-weight: bold;
  }
  .label-wrapper {
    display: flex;
    justify-content: space-between;
  }

  input {
    margin-bottom: 26px;
  }

  .divider {
    position: relative;
    text-align: center;
    margin: 2rem 0;

    hr {
      border: none;
      height: 1px;
      background-color: #ccc;
    }

    span {
      position: absolute;
      top: 50%;
      left: 50%;
      background-color: #f5f5f5;
      padding: 0 1rem;
      transform: translate(-50%, -50%);
      font-size: 0.9rem;
      color: #666;
    }
  }

  .signup-text {
    text-align: center;
    font-size: 0.9rem;
    color: #333;
  }

  .error {
    color: red;
    font-size: 0.85rem;
    margin-top: -16px;
    margin-bottom: 12px;
  }
`;

export const ImageContainer = styled.div<ImageContainerProps>`
  flex: 1;
  background: url(${(props) => props.background}) no-repeat center center;
  background-size: cover;
  background-color: #ece9e4;
`;
