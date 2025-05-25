import { useState } from "react";
import ListBackground from "../../assets/ReportBackground.jpg";
import { Button } from "../../components/Button/Button";
import { Header } from "../../components/Header/Header";
import { Modal } from "../../components/Modal/modal";
import { ReportList } from "../../components/ReportList/ReportList";
import * as S from "./styles";

export const Dashboard = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <S.Container>
      <Header />

      <S.TopSection id="formulario">
        <div>
          <h2>Caiu em um golpe? Denuncie aqui!</h2>
          <p>
            Se você foi vítima ou identificou uma tentativa de golpe, denuncie
            agora mesmo.
          </p>
        </div>
        <Button onClick={() => setIsModalOpen(true)}>Fazer Denúncia</Button>
      </S.TopSection>

      <S.BottomSection id="denuncias" background={ListBackground}>
        <ReportList />
      </S.BottomSection>

      {isModalOpen && <Modal onClose={() => setIsModalOpen(false)} />}
    </S.Container>
  );
};
