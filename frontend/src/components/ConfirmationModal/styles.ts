// src/components/ConfirmationModal/styles.ts
import styled from "styled-components";

export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* Certifique-se de que está acima de outros elementos */
`;

export const ModalContainer = styled.div`
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 400px;
  width: 90%;
`;

export const ButtonGroup = styled.div`
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 15px; /* Espaço entre os botões */
`;

export const ConfirmButton = styled.button`
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #dc3545; /* Cor de perigo (vermelho) */
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;

  &:hover {
    background-color: #c82333;
  }
`;

export const CancelButton = styled.button`
  padding: 10px 20px;
  border: 1px solid #6c757d;
  border-radius: 5px;
  background-color: transparent;
  color: #6c757d;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    background-color: #e2e6ea;
    color: #495057;
  }
`;
