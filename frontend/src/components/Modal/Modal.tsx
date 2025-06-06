import axios, { AxiosError } from "axios"; // Importe AxiosError
import { useState } from "react";
import { toast } from "react-toastify"; // Certifique-se de que toast está importado
import * as Yup from "yup";
import { useAuth } from "../../hooks/useAuth";
import {
  createReport,
  saveToSpecificReportTable,
} from "../../services/reportService";
import { ReportType, ReportTypeLabel } from "../../utils/enums/ReportType";
import { formatValue } from "../../utils/format";
import type { Report } from "../ReportList/ReportList";
import * as S from "./styles";

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
          /^(https?:\/\/)?(www\.)?[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]{2,}(\/.*)?$/,
          "URL inválida (ex: https://exemplo.com)",
        ),
      };
      await (
        schemaMap[type]?.required("Campo obrigatório") ||
        Yup.string().required("Campo obrigatório")
      ).validate(value.trim());
      return true;
    } catch (err: any) {
      // Alterado de alert para toast.error
      toast.error(err.message);
      return false;
    }
  };

  const removeMask = (value: string): string =>
    value.replace(/[.\-()/\s]/g, "");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedType) {
      toast.error("Por favor, selecione um tipo de denúncia.");
      return;
    }

    const isValid = await validate(selectedType, data);
    if (!isValid) return; // A validação já exibiu o toast de erro

    const cleanValue = ["CPF", "CNPJ", "CELLPHONE"].includes(selectedType)
      ? removeMask(data)
      : data.trim();
    if (!user) {
      toast.error("Você precisa estar logado para enviar a denúncia.");
      return;
    }

    try {
      const saved = await createReport(user.id, {
        reportType: selectedType,
        reportValue: cleanValue,
      });

      await saveToSpecificReportTable(selectedType, cleanValue);

      const reportTypeCode = ReportType[selectedType];

      const formattedReport: Report = {
        id: saved.id,
        reportType: reportTypeCode,
        rawValue: cleanValue,

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
      console.error(err);

      let errorMessage = "Erro inesperado ao enviar denúncia.";

      if (axios.isAxiosError(err)) {
        const axiosError = err as AxiosError;

        if (axiosError.response) {
          if (typeof axiosError.response.data === "string") {
            errorMessage = axiosError.response.data;
          } else if (
            typeof axiosError.response.data === "object" &&
            axiosError.response.data !== null &&
            "message" in axiosError.response.data &&
            typeof (axiosError.response.data as any).message === "string"
          ) {
            errorMessage = (axiosError.response.data as any).message;
          } else if (
            typeof axiosError.response.data === "object" &&
            axiosError.response.data !== null &&
            "errors" in axiosError.response.data &&
            Array.isArray((axiosError.response.data as any).errors)
          ) {
            const validationErrors = (axiosError.response.data as any).errors
              .map((error: any) => error.defaultMessage || error.message)
              .filter(Boolean);
            if (validationErrors.length > 0) {
              errorMessage = validationErrors.join(", ");
            }
          }
        } else if (axiosError.request) {
          errorMessage = "Nenhuma resposta do servidor. Verifique sua conexão.";
        } else {
          errorMessage = axiosError.message;
        }
      } else if (err instanceof Error) {
        errorMessage = err.message;
      }

      toast.error(errorMessage);
    }
  };

  return (
    <S.Overlay>
      <S.ModalContainer>
        <S.CloseButton onClick={onClose}>×</S.CloseButton>

        {!selectedType ? (
          <>
            <h2>Denunciar {selectedType}</h2>
            <S.TypeList>
              {Object.keys(ReportType).map((type) => (
                <button
                  key={type}
                  onClick={() =>
                    setSelectedType(type as keyof typeof ReportType)
                  }
                >
                  {ReportTypeLabel[ReportType[type as keyof typeof ReportType]]}
                </button>
              ))}
            </S.TypeList>
          </>
        ) : (
          <>
            <h2>Denunciar {ReportTypeLabel[ReportType[selectedType]]}</h2>
            <form onSubmit={handleSubmit}>
              <label>
                Informe o {ReportTypeLabel[ReportType[selectedType]]}:
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
