import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import ListBackground from "../../assets/ReportBackground.jpg";
import { Button } from "../../components/Button/Button";
import { Header } from "../../components/Header/Header";
import { Modal } from "../../components/Modal/Modal";
import {
  type Report,
  ReportList,
} from "../../components/ReportList/ReportList";

import { ReportType } from "../../utils/enums/ReportType";

import * as S from "./styles";

export const Dashboard = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [reports, setReports] = useState<Report[]>([
    {
      id: 1,
      reportType: ReportType.CPF,
      dataValue: "123.456.789-00",
      date: "Hoje, 14:22",
    },
    {
      id: 2,
      reportType: ReportType.TELEFONE,
      dataValue: "(11) 91234-5678",
      date: "Ontem, 09:10",
    },
    {
      id: 3,
      reportType: ReportType.URL,
      dataValue: "http://site-falso.com.br/",
      date: "12/07/2025, 18:45",
    },
    {
      id: 4,
      reportType: ReportType.TELEFONE,
      dataValue: "(11) 987891244",
      date: "12/07/2025, 18:45",
    },
    {
      id: 5,
      reportType: ReportType.CNPJ,
      dataValue: "14.725.528/0001-32",
      date: "11/07/2025, 12:25",
    },
    {
      id: 6,
      reportType: ReportType.EMAIL,
      dataValue: "reported@gmail.com",
      date: "09/04/2025, 08:11",
    },
    // ... (demais itens que você tinha antes)
  ]);

  const handleDelete = (id: number) => {
    setReports((prev) => prev.filter((r) => r.id !== id));
    toast.success("Denúncia removida com sucesso!");
  };

  useEffect(() => {
    const scrollTarget = localStorage.getItem("scrollTo");
    if (scrollTarget) {
      const section = document.getElementById(scrollTarget);
      if (section) {
        section.scrollIntoView({ behavior: "smooth" });
      }
      localStorage.removeItem("scrollTo");
    }
  }, []);

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
        <Button
          onClick={() => {
            setIsModalOpen(true);
          }}
        >
          Fazer Denúncia
        </Button>
      </S.TopSection>

      <S.BottomSection id="denuncias" background={ListBackground}>
        <ReportList reports={reports} onDelete={handleDelete} />
      </S.BottomSection>

      {isModalOpen && <Modal onClose={() => setIsModalOpen(false)} />}
    </S.Container>
  );
};
