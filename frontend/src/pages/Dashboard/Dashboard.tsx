import ListBackground from "../../assets/ReportBackground.jpg";
import { Button } from "../../components/Button/Button";
import { Header } from "../../components/Header/Header";
import { ReportList } from "../../components/ReportList/ReportList";
import * as S from "./styles";

export const Dashboard = () => {
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
        <Button onClick={() => console.log("Abrir modal")}>
          Fazer Denúncia
        </Button>
      </S.TopSection>

      <S.BottomSection id="denuncias" background={ListBackground}>
        <ReportList />
      </S.BottomSection>
    </S.Container>
  );
};
