// src/components/Button.tsx

import { StyledButton } from "./styles";

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
  type?: "button" | "submit";
}

export const Button = ({ children, onClick, type = "button" }: ButtonProps) => {
  return (
    <StyledButton onClick={onClick} type={type}>
      {children}
    </StyledButton>
  );
};
