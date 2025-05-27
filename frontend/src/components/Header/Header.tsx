import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Logo from "../../assets/Safeshield2.png";
import { useAuth } from "../../hooks/useAuth";
import * as S from "./styles";

export const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [menuOpen, setMenuOpen] = useState(false);
  const { user, logout } = useAuth();

  const scrollToSection = (id: string) => {
    const section = document.getElementById(id);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  const handleNavClick = (target: string, e?: React.MouseEvent) => {
    e?.preventDefault();

    if (location.pathname !== "/") {
      localStorage.setItem("scrollTo", target);
    } else {
      scrollToSection(target);
    }

    setMenuOpen(false);
  };

  return (
    <S.Container>
      <S.LogoWrapper>
        <img src={Logo} alt="SafePix Logo" />
      </S.LogoWrapper>
      <p>Olá, {user?.name?.split(" ")[0] || "usuário"}!</p>
      <S.Hamburger onClick={() => setMenuOpen(!menuOpen)}>☰</S.Hamburger>

      <S.Navi open={menuOpen}>
        <S.NavLink onClick={() => handleNavClick("formulario")}>
          Denunciar um golpe
        </S.NavLink>
        <S.NavLink onClick={() => handleNavClick("denuncias")}>
          Minhas denúncias
        </S.NavLink>
        <S.LogoutButton
          onClick={() => {
            logout();
            navigate("/");
          }}
        >
          Sair
        </S.LogoutButton>
      </S.Navi>
    </S.Container>
  );
};
