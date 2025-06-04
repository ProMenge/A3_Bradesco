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

import * as S from "./styles";

export const Dashboard = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [reports, setReports] = useState<Report[]>([]);

  const { user } = useAuth();

  const handleDelete = async (id: number) => {
    try {
      if (!user) return;

      await deleteReport(user.id, id);
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
        if (!user) return;

        const userReports = await getUserReports(user.id);
        setReports(userReports);
      } catch (err) {
        toast.error("Erro ao buscar denúncias.");
        console.error(err);
      }
    };

    fetchReports();
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
