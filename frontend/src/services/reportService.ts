import type { Report } from "../components/ReportList/ReportList";
import {
  ReportTypeCodeFromLabel,
  type ReportTypeValue,
} from "../utils/enums/ReportType";
import { formatValue } from "../utils/format";
import { api } from "./api";

export interface ReportDTO {
  id: number;
  reportType: string;
  reportValue: string;
  reporter: {
    id: number;
    name: string;
    cpf: string;
    email: string;
    password?: string;
  };
}

export interface CreateReportDTO {
  reportType: string; // agora string como "EMAIL", "SITE" etc.
  reportValue: string;
}

export const getUserReports = async (userId: number): Promise<Report[]> => {
  const res = await api.get(`/users/${userId}/user-reports`);
  const reports = res.data;

  return reports.map((r: any) => {
    const typeCode = ReportTypeCodeFromLabel[r.reportType]; // "EMAIL" → 3
    return {
      id: r.id,
      reportType: typeCode as ReportTypeValue,
      dataValue: formatValue(typeCode, r.reportValue),
      date: new Date(r.reportMoment).toLocaleString("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
      }),
    };
  });
};

export const createReport = async (
  userId: number,
  reportData: CreateReportDTO,
): Promise<ReportDTO> => {
  console.log("dados enviados", reportData);
  const res = await api.post(`/users/${userId}/user-reports`, reportData);
  console.log("dados enviados", res);
  return res.data;
};

// reportService.ts
export const deleteReport = async (
  userId: number,
  reportId: number,
): Promise<void> => {
  try {
    const response = await api.delete(
      `/users/${userId}/user-reports/${reportId}`,
    );

    if (response.status === 200 || response.status === 204) return;

    if (
      response.status === 400 &&
      (!response.data || Object.keys(response.data).length === 0)
    ) {
      console.warn("Resposta 400 vazia considerada como sucesso.");
      return;
    }

    throw new Error("Falha ao deletar denúncia");
  } catch (error: any) {
    console.error("Erro no deleteReport:", error);
    throw error;
  }
};
