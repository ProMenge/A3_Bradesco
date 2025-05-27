export const ReportType = {
  CPF: "CPF",
  CNPJ: "CNPJ",
  URL: "URL/Site",
  EMAIL: "Email",
  TELEFONE: "Telefone",
  CELLPHONE: "CELLPHONE"
} as const;

export type ReportTypeKey = keyof typeof ReportType;
export type ReportTypeValue = (typeof ReportType)[ReportTypeKey];
