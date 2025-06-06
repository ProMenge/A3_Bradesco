// src/components/ConfirmationModal/ConfirmationModal.tsx
import React from "react";
import * as S from "./styles"; // Importa todos os estilos de styles.ts

interface ConfirmationModalProps {
  message: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export const ConfirmationModal: React.FC<ConfirmationModalProps> = ({
  message,
  onConfirm,
  onCancel,
}) => {
  return (
    <S.Overlay>
      <S.ModalContainer>
        <p>{message}</p>
        <S.ButtonGroup>
          <S.CancelButton onClick={onCancel}>Cancelar</S.CancelButton>
          <S.ConfirmButton onClick={onConfirm}>Confirmar</S.ConfirmButton>
        </S.ButtonGroup>
      </S.ModalContainer>
    </S.Overlay>
  );
};
