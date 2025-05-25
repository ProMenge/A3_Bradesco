import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Logo from "../../assets/Safeshield2.png";
import * as S from "./styles";

export const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [menuOpen, setMenuOpen] = useState(false);

  const scrollToSection = (id: string) => {
    const section = document.getElementById(id);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  const handleNavClick = (target: string) => {
    if (location.pathname !== "/") {
      navigate("/");
      // aguarda render e faz scroll
      setTimeout(() => scrollToSection(target), 100);
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

      <S.Hamburger onClick={() => setMenuOpen(!menuOpen)}>☰</S.Hamburger>

      <S.Navi open={menuOpen}>
        <S.NavLink onClick={() => handleNavClick("formulario")}>
          Denunciar um golpe
        </S.NavLink>
        <S.NavLink onClick={() => handleNavClick("denuncias")}>
          Minhas denúncias
        </S.NavLink>
        <S.LogoutButton onClick={() => console.log("Deslogar")}>
          Sair
        </S.LogoutButton>
      </S.Navi>
    </S.Container>
  );
};
