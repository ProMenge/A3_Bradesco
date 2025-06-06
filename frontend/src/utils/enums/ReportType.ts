// Valores representando os tipos de denúncia
export const ReportType = {
  CPF: 1,
  CNPJ: 2,
  EMAIL: 3,
  SITE: 4,
  CELLPHONE: 5,
} as const;

// Tipo que representa um valor válido do enum (1 a 5)
export type ReportTypeValue = (typeof ReportType)[keyof typeof ReportType];

// Labels legíveis para o usuário final
export const ReportTypeLabel: Record<ReportTypeValue, string> = {
  [ReportType.CPF]: "CPF",
  [ReportType.CNPJ]: "CNPJ",
  [ReportType.EMAIL]: "EMAIL",
  [ReportType.SITE]: "SITE",
  [ReportType.CELLPHONE]: "Celular",
};

// (Opcional) Labels invertidos para converter de string para código
export const ReportTypeCodeFromLabel: Record<string, ReportTypeValue> = {
  CPF: ReportType.CPF,
  CNPJ: ReportType.CNPJ,
  EMAIL: ReportType.EMAIL,
  SITE: ReportType.SITE,
  CELLPHONE: ReportType.CELLPHONE,
};

export const ReportTypeLabelFromCode: Record<
  ReportTypeValue,
  keyof typeof ReportType
> = {
  1: "CPF",
  2: "CNPJ",
  3: "EMAIL",
  4: "SITE",
  5: "CELLPHONE",
};
