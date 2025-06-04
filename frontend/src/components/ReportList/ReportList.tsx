import {
  ReportTypeLabel,
  type ReportTypeValue,
} from "../../utils/enums/ReportType";
import * as S from "./styles";

export interface Report {
  id: number;
  reportType: ReportTypeValue;
  dataValue: string;
  date: string;
  rawValue: string;
}
interface ReportListProps {
  reports: Report[];
  onDelete: (id: number, type: ReportTypeValue, rawValue: string) => void;
}

export const ReportList = ({ reports, onDelete }: ReportListProps) => {
  return (
    <S.ListWrapper>
      <h3>Minhas denúncias</h3>
      <ul>
        {reports.map((report) => (
          <S.ReportItem key={report.id}>
            <div className="left">
              <strong>{report.date}</strong>
            </div>

            <div className="right">
              <div className="title-line">
                <h4>Golpe via Pix</h4>
                <S.Badge type={ReportTypeLabel[report.reportType]}>
                  {ReportTypeLabel[report.reportType]}
                </S.Badge>
              </div>
              <p>
                Dado denunciado: <strong>{report.dataValue}</strong>
              </p>
            </div>

            <div className="actions">
              <S.ActionButton
                onClick={() =>
                  onDelete(report.id, report.reportType, report.rawValue)
                }
                delete
              >
                Retirar Denúncia
              </S.ActionButton>
            </div>
          </S.ReportItem>
        ))}
      </ul>
    </S.ListWrapper>
  );
};
