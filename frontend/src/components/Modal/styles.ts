import { IMaskInput } from "react-imask";
import styled from "styled-components";

export const Overlay = styled.div`
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
`;

export const ModalContainer = styled.div`
  background: white;
  padding: 2rem;
  border-radius: 12px;
  max-width: 500px;
  width: 90%;
  position: relative;

  h2 {
    margin-bottom: 1.5rem;
  }

  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  label {
    font-size: 1rem;
  }
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 1rem;
  right: 1.2rem;
  font-size: 1.5rem;
  background: none;
  border: none;
  cursor: pointer;
`;

export const TypeList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  button {
    padding: 0.75rem;
    border-radius: 6px;
    font-weight: bold;
    border: none;
    background: #eee;
    cursor: pointer;

    &:hover {
      background: #ddd;
    }
  }
`;

export const Input = styled.input`
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
`;

export const Buttons = styled.div`
  display: flex;
  justify-content: space-between;

  button {
    padding: 0.6rem 1.2rem;
    font-weight: bold;
    cursor: pointer;
    border: none;
    border-radius: 6px;
  }

  button:first-child {
    background: #4caf50;
    color: white;
  }

  button:last-child {
    background: transparent;
    border: 1px solid #999;
    color: #333;
  }
`;

export const MaskedInput = styled(IMaskInput)`
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  font-family: inherit;
`;
