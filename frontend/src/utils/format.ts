import type { ReportTypeValue } from "./enums/ReportType";
import { ReportType } from "./enums/ReportType";

export function formatValue(type: ReportTypeValue, value: string): string {
  switch (type) {
    case ReportType.CPF:
      return value.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, "$1.$2.$3-$4");
    case ReportType.CNPJ:
      return value.replace(
        /^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/,
        "$1.$2.$3/$4-$5"
      );
    case ReportType.CELLPHONE:
      return value.replace(/^(\d{2})(\d{5})(\d{4})$/, "($1) $2-$3");
    case ReportType.CELLPHONE:
      return value.replace(/^(\d{2})(\d{5})(\d{4})$/, "($1) $2-$3");
    default:
      return value;
  }
}
