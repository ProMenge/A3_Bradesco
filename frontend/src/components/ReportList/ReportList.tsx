import { ReportType, type ReportTypeValue } from "../../utils/enums/ReportType";
import * as S from "./styles";

interface Report {
  id: number;
  reportType: ReportTypeValue;
  dataValue: string;
  date: string;
}

export const ReportList = () => {
  const reports: Report[] = [
    {
      id: 1,
      reportType: ReportType.CPF,
      dataValue: "123.456.789-00",
      date: "Hoje, 14:22",
    },
    {
      id: 2,
      reportType: ReportType.TELEFONE,
      dataValue: "(11) 91234-5678",
      date: "Ontem, 09:10",
    },
    {
      id: 3,
      reportType: ReportType.URL,
      dataValue: "http://site-falso.com.br",
      date: "12/07/2025, 18:45",
    },
    {
      id: 4,
      reportType: ReportType.TELEFONE,
      dataValue: "(11) 987891244",
      date: "12/07/2025, 18:45",
    },
    {
      id: 5,
      reportType: ReportType.CNPJ,
      dataValue: "14.725.528/0001-32",
      date: "11/07/2025, 12:25",
    },
    {
      id: 6,
      reportType: ReportType.EMAIL,
      dataValue: "reported@gmail.com",
      date: "09/04/2025, 08:11",
    },
  ];

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
                <S.Badge type={report.reportType}>{report.reportType}</S.Badge>
              </div>
              <p>
                Dado denunciado: <strong>{report.dataValue}</strong>
              </p>
            </div>
          </S.ReportItem>
        ))}
      </ul>
    </S.ListWrapper>
  );
};
