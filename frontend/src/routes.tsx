import { Route, Routes } from "react-router-dom";
import { Dashboard } from "./pages/Dashboard/Dashboard";
import { Login } from "./pages/Login/Login";
// import { Dashboard } from './pages/Dashboard'; // exemplo futuro

const Rotas = () => (
  <Routes>
    <Route path="/" element={<Login />} />
    <Route path="/dash" element={<Dashboard />} />
  </Routes>
);

export default Rotas;
