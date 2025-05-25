import { useState } from "react";
import { toast } from "react-toastify";
import * as Yup from "yup";
import { ReportType } from "../../utils/enums/ReportType";
import * as S from "./styles";

interface ModalProps {
  onClose: () => void;
}

export const Modal = ({ onClose }: ModalProps) => {
  const [selectedType, setSelectedType] = useState<string | null>(null);
  const [data, setData] = useState("");

  const getMaskByType = (type: string): string => {
    switch (type) {
      case "CPF":
        return "000.000.000-00";
      case "CNPJ":
        return "00.000.000/0000-00";
      case "Telefone":
        return "(00) 00000-0000";
      default:
        return ""; // Email e URL não precisam de máscara
    }
  };

  const validate = async (type: string, value: string): Promise<boolean> => {
    try {
      const base = value.trim();

      let schema: Yup.StringSchema = Yup.string().required("Campo obrigatório");

      switch (type) {
        case "CPF":
          schema = schema.matches(
            /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
            "CPF inválido",
          );
          break;
        case "CNPJ":
          schema = schema.matches(
            /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/,
            "CNPJ inválido",
          );
          break;
        case "Telefone":
          schema = schema.matches(
            /^\(\d{2}\)\s\d{5}-\d{4}$/,
            "Telefone inválido",
          );
          break;
        case "Email":
          schema = Yup.string().email("E-mail inválido").required();
          break;
        case "URL":
          schema = Yup.string().url("URL inválida").required();
          break;
      }

      await schema.validate(base);
      return true;
    } catch (err: any) {
      alert(err.message);
      return false;
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const isValid = await validate(selectedType!, data);
      if (!isValid) return;

      // Simula envio de denúncia (aqui entraria sua lógica futura de API)
      console.log("Tipo:", selectedType);
      console.log("Valor denunciado:", data);
      console.log(toast);

      toast.success("Denúncia enviada com sucesso!");
      onClose();
    } catch (err: any) {
      toast.error("Ocorreu um erro ao enviar a denúncia.");
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
              {Object.values(ReportType).map((type) => (
                <button key={type} onClick={() => setSelectedType(type)}>
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
                {["CPF", "CNPJ", "Telefone"].includes(selectedType) ? (
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
