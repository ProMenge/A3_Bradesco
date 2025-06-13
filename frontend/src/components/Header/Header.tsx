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

  const handleNavLinkClick = (
    target: string,
    e?: React.MouseEvent,
    shouldOpenModal: boolean = false,
  ) => {
    e?.preventDefault();
    setMenuOpen(false);

    if (location.pathname !== "/dashboard") {
      // Se não estiver no Dashboard, navega para lá com o estado
      navigate("/dashboard", {
        state: { openReportModal: shouldOpenModal, scrollTo: target },
      });
    } else {
      if (shouldOpenModal) {
        navigate(location.pathname, {
          replace: true,
          state: { openReportModal: true, scrollTo: target },
        });
      } else {
        scrollToSection(target);
      }
    }
  };

  return (
    <S.Container>
      <S.LogoWrapper>
        <img src={Logo} alt="SafePix Logo" />
      </S.LogoWrapper>
      <p>Olá, {user?.name?.split(" ")[0] || "usuário"}!</p>
      <S.Hamburger onClick={() => setMenuOpen(!menuOpen)}>☰</S.Hamburger>

      <S.Navi open={menuOpen}>
        <S.NavLink onClick={(e) => handleNavLinkClick("formulario", e, true)}>
          Denunciar um golpe
        </S.NavLink>

        <S.NavLink onClick={() => handleNavLinkClick("denuncias")}>
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
