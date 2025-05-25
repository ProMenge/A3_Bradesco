import styled from "styled-components";

interface BadgeProps {
  type: string;
}

interface NavProps {
  open: boolean;
}

export const ListWrapper = styled.div`
  h3 {
    font-size: 1.61rem;
    margin-bottom: 1.5rem;
    color: #fff;
    text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.6);
  }

  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }
`;

export const ReportItem = styled.li`
  display: flex;
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 1rem 1.5rem;
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);

  .left {
    margin-right: 1.5rem;
    min-width: 170px;
    font-weight: bold;
    color: #4a4a4a;
    font-size: 1.2rem;
  }

  .right {
    h4 {
      margin: 0;
      font-size: 1.2rem;
    }

    p {
      margin: 0.5rem 0 0;
      color: #666;
    }
  }

  .title-line {
    display: flex;
    align-items: center;
    gap: 0.5rem;

    h4 {
      margin: 0;
    }
  }
`;

export const Badge = styled.span<BadgeProps>`
  display: inline-block;
  margin-left: 1rem;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: bold;
  color: white;
  background-color: ${({ type }) => {
    switch (type) {
      case "CPF":
        return "#5c6bc0"; // azul
      case "CNPJ":
        return "#009688"; // teal
      case "Email":
        return "#f9a825"; // amarelo
      case "Telefone":
        return "#43a047"; // verde
      case "URL":
        return "#e53935"; // vermelho
      default:
        return "#757575"; // cinza
    }
  }};
`;
