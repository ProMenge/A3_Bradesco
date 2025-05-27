export const ReportType = {
  CPF: "CPF",
  CNPJ: "CNPJ",
  URL: "URL/Site",
  SITE: "SITE",
  EMAIL: "EMAIL",
  TELEFONE: "Telefone",
  CELLPHONE: "CELLPHONE",
} as const;

export type ReportTypeKey = keyof typeof ReportType;
export type ReportTypeValue = (typeof ReportType)[ReportTypeKey];
