import { Navigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

interface Props {
  children: React.ReactNode;
}

export const PrivateRoute = ({ children }: Props) => {
  const { user } = useAuth();

  // Se não estiver logado, redireciona
  if (!user) return <Navigate to="/" replace />;

  return <>{children}</>;
};
