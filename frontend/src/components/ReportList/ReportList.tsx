import { useState } from "react"; // Importe useState
import {
  ReportTypeLabel,
  type ReportTypeValue,
} from "../../utils/enums/ReportType";
import { ConfirmationModal } from "../ConfirmationModal/ConfirmationModal"; // Importe o novo modal
import * as S from "./styles";

export interface Report {
  id: number;
  reportType: ReportTypeValue;
  dataValue: string;
  date: string;
  rawValue: string;
}

interface ReportListProps {
  reports: Report[];
  onDelete: (id: number, type: ReportTypeValue, rawValue: string) => void;
}

export const ReportList = ({ reports, onDelete }: ReportListProps) => {
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [reportToDelete, setReportToDelete] = useState<Report | null>(null);

  const handleOpenConfirmModal = (report: Report) => {
    setReportToDelete(report);
    setShowConfirmModal(true);
  };

  const handleConfirmDelete = () => {
    if (reportToDelete) {
      onDelete(
        reportToDelete.id,
        reportToDelete.reportType,
        reportToDelete.rawValue,
      );
      setReportToDelete(null); // Limpa o estado
      setShowConfirmModal(false); // Fecha o modal
    }
  };

  const handleCancelDelete = () => {
    setReportToDelete(null); // Limpa o estado
    setShowConfirmModal(false); // Fecha o modal
  };

  return (
    <S.ListWrapper>
      <h3>Minhas denúncias</h3>
      <ul>
        {reports.map((report) => (
          <S.ReportItem key={report.id}>
            <div className="left">
              <strong>{report.date}</strong>
            </div>

            <div className="right">
              <div className="title-line">
                <h4>Golpe via Pix</h4>{" "}
                {/* Ajuste se este título é fixo ou dinâmico */}
                <S.Badge type={ReportTypeLabel[report.reportType]}>
                  {ReportTypeLabel[report.reportType]}
                </S.Badge>
              </div>
              <p>
                Dado denunciado: <strong>{report.dataValue}</strong>
              </p>
            </div>

            <div className="actions">
              <S.ActionButton
                onClick={() => handleOpenConfirmModal(report)} // Chama a função para abrir o modal de confirmação
                delete
              >
                Retirar Denúncia
              </S.ActionButton>
            </div>
          </S.ReportItem>
        ))}
      </ul>

      {/* Renderiza o modal de confirmação condicionalmente */}
      {showConfirmModal && reportToDelete && (
        <ConfirmationModal
          message={`Tem certeza que deseja remover a denúncia do ${ReportTypeLabel[reportToDelete.reportType]}: ${reportToDelete.dataValue}?`}
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
        />
      )}
    </S.ListWrapper>
  );
};
