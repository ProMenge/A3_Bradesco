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
  reportType: string;
  reportValue: string;
}

// GET /user-reports
export const getUserReports = async (): Promise<ReportDTO[]> => {
  const res = await api.get("/user-reports");
  return res.data;
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
