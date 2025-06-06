import { Route, Routes } from "react-router-dom";
import { Dashboard } from "./pages/Dashboard/Dashboard";
import { Login } from "./pages/Login/Login";
import { PrivateRoute } from "./routes/PrivateRoute";
// import { Dashboard } from './pages/Dashboard'; // exemplo futuro

const Rotas = () => (
  <Routes>
    <Route path="/" element={<Login />} />
    <Route
      path="/dashboard"
      element={
        <PrivateRoute>
          {" "}
          <Dashboard />{" "}
        </PrivateRoute>
      }
    />
  </Routes>
);

export default Rotas;
