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
import { useAuth } from "../../hooks/useAuth";
import { deleteReport, getUserReports } from "../../services/reportService";
import { formatValue } from "../../utils/format";

import { type ReportTypeValue } from "../../utils/enums/ReportType";

import * as S from "./styles";

export const Dashboard = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [reports, setReports] = useState<Report[]>([]);

  const { user } = useAuth();

  const handleDelete = async (id: number) => {
    try {
      await deleteReport(id);
      setReports((prev) => prev.filter((r) => r.id !== id));
      toast.success("Denúncia removida com sucesso!");
    } catch (err: any) {
      console.error(err);
      toast.error("Erro ao remover denúncia. Tente novamente.");
    }
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

    const fetchReports = async () => {
      try {
        const allReports = await getUserReports();

        // Filtra apenas os reports do usuário logado
        const userReports = allReports
          .filter((r) => r.reporter.id === user?.id)
          .map((r) => ({
            id: r.id,
            reportType: Number(r.reportType) as ReportTypeValue,
            dataValue: formatValue(
              Number(r.reportType) as ReportTypeValue,
              r.reportValue,
            ),
            date: new Date().toLocaleString("pt-BR", {
              day: "2-digit",
              month: "2-digit",
              year: "numeric",
              hour: "2-digit",
              minute: "2-digit",
            }),
          }));

        setReports(userReports);
      } catch (err) {
        toast.error("Erro ao buscar denúncias.");
        console.error(err);
      }
    };

    if (user) {
      fetchReports();
    }
  }, [user]);

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

      {isModalOpen && (
        <Modal
          onClose={() => setIsModalOpen(false)}
          onAdd={(newReport) => setReports((prev) => [...prev, newReport])}
        />
      )}
    </S.Container>
  );
};
