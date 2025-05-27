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
export const createReport = async (reportData: CreateReportDTO): Promise<ReportDTO> => {
  const res = await api.post("/user-reports", reportData);
  return res.data;
};
