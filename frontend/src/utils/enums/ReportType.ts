export const ReportType = {
  CPF: "CPF",
  CNPJ: "CNPJ",
  URL: "URL/Site",
  EMAIL: "Email",
  TELEFONE: "Telefone",
} as const;

export type ReportTypeKey = keyof typeof ReportType;
export type ReportTypeValue = (typeof ReportType)[ReportTypeKey];
