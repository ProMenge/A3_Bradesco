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
  reporterId: number;
  reportTypeCode: number;
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

// POST /user-reports
export const createReport = async (
  reportData: CreateReportDTO,
): Promise<ReportDTO> => {
  const res = await api.post("/user-reports", reportData);
  return res.data;
};

// reportService.ts
export const deleteReport = async (reportId: number): Promise<void> => {
  try {
    const response = await api.delete(`/user-reports/${reportId}`);

    // Se for 200 ou 204, ok
    if (response.status === 200 || response.status === 204) return;

    // Se for 400 mas sem body, considera sucesso
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
