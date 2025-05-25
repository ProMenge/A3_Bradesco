import { Route, Routes } from "react-router-dom";
import { Login } from "./pages/Login/Login";
// import { Dashboard } from './pages/Dashboard'; // exemplo futuro

const Rotas = () => (
  <Routes>
    <Route path="/" element={<Login />} />
  </Routes>
);

export default Rotas;
