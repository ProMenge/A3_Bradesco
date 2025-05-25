import { BrowserRouter } from "react-router-dom";
import Rotas from "./routes";
import { GlobalCss } from "./styles";

function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalCss />
        <Rotas />
      </BrowserRouter>
    </>
  );
}

export default App;
