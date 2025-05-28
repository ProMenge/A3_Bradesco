import { useState } from "react";
import { toast } from "react-toastify";
import * as Yup from "yup";
import { ReportType } from "../../utils/enums/ReportType";
import { useAuth } from "../../hooks/useAuth";
import { createReport } from "../../services/reportService";
import * as S from "./styles";
import { formatValue } from "../../utils/format";
import type { Report } from "../ReportList/ReportList";

interface ModalProps {
  onClose: () => void;
  onAdd: (report: Report) => void;
}

export const Modal = ({ onClose, onAdd }: ModalProps) => {
  const [selectedType, setSelectedType] = useState<
    keyof typeof ReportType | null
  >(null);
  const [data, setData] = useState("");
  const { user } = useAuth();

  const getMaskByType = (type: string): string => {
    switch (type) {
      case "CPF":
        return "000.000.000-00";
      case "CNPJ":
        return "00.000.000/0000-00";
      case "CELLPHONE":
        return "(00)00000-0000";
      default:
        return "";
    }
  };

  const validate = async (type: string, value: string): Promise<boolean> => {
    try {
      const schemaMap: Record<string, Yup.StringSchema> = {
        CPF: Yup.string().matches(
          /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
          "CPF inválido",
        ),
        CNPJ: Yup.string().matches(
          /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/,
          "CNPJ inválido",
        ),
        CELLPHONE: Yup.string().matches(
          /^\(\d{2}\)\s?\d{5}-\d{4}$/,
          "Telefone inválido",
        ),
        EMAIL: Yup.string().email("E-mail inválido"),
        SITE: Yup.string().matches(
          /^(?!:\/\/)([a-zA-Z0-9-_]+\.)+[a-zA-Z]{2,}$/,
          "URL inválida",
        ),
      };
      await (schemaMap[type]?.required() || Yup.string().required()).validate(
        value.trim(),
      );
      return true;
    } catch (err: any) {
      alert(err.message);
      return false;
    }
  };

  const removeMask = (value: string): string =>
    value.replace(/[.\-()/\s]/g, "");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedType) return;

    const isValid = await validate(selectedType, data);
    if (!isValid) return;

    const cleanValue = removeMask(data);
    if (!user) {
      toast.error("Você precisa estar logado para enviar a denúncia.");
      return;
    }

    try {
      const reportTypeCode = ReportType[selectedType]; // isso gera o número

      const saved = await createReport({
        reporterId: user.id,
        reportTypeCode,
        reportValue: cleanValue,
      });

      const formattedReport: Report = {
        id: saved.id,
        reportType: reportTypeCode,
        dataValue: formatValue(reportTypeCode, cleanValue),
        date: new Date().toLocaleString("pt-BR", {
          day: "2-digit",
          month: "2-digit",
          year: "numeric",
          hour: "2-digit",
          minute: "2-digit",
        }),
      };

      onAdd(formattedReport);
      toast.success("Denúncia enviada com sucesso!");
      onClose();
    } catch (err) {
      toast.error("Erro ao enviar denúncia.");
      console.error(err);
    }
  };

  return (
    <S.Overlay>
      <S.ModalContainer>
        <S.CloseButton onClick={onClose}>×</S.CloseButton>

        {!selectedType ? (
          <>
            <h2>Selecione o tipo de denúncia</h2>
            <S.TypeList>
              {Object.keys(ReportType).map((type) => (
                <button
                  key={type}
                  onClick={() =>
                    setSelectedType(type as keyof typeof ReportType)
                  }
                >
                  {type}
                </button>
              ))}
            </S.TypeList>
          </>
        ) : (
          <>
            <h2>Denunciar {selectedType}</h2>
            <form onSubmit={handleSubmit}>
              <label>
                Informe o {selectedType.toUpperCase()}:
                {["CPF", "CNPJ", "CELLPHONE"].includes(selectedType) ? (
                  <S.MaskedInput
                    mask={getMaskByType(selectedType)}
                    value={data}
                    onAccept={(value) => setData(value as string)}
                    overwrite
                    required
                  />
                ) : (
                  <S.Input
                    value={data}
                    onChange={(e) => setData(e.target.value)}
                    required
                  />
                )}
              </label>
              <S.Buttons>
                <button type="submit">Enviar denúncia</button>
                <button type="button" onClick={() => setSelectedType(null)}>
                  Voltar
                </button>
              </S.Buttons>
            </form>
          </>
        )}
      </S.ModalContainer>
    </S.Overlay>
  );
};
