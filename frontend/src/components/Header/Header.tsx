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

  // Esta função agora será usada apenas para navegação com rolagem sem modal
  const scrollToSection = (id: string) => {
    const section = document.getElementById(id);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  const handleNavLinkClick = (
    target: string, // 'formulario' ou 'denuncias'
    e?: React.MouseEvent,
    shouldOpenModal: boolean = false, // Mudança de nome para clareza
  ) => {
    e?.preventDefault();
    setMenuOpen(false); // Fecha o menu hambúrguer

    if (location.pathname !== "/dashboard") {
      // Se não estiver no Dashboard, navega para lá com o estado
      navigate("/dashboard", {
        state: { openReportModal: shouldOpenModal, scrollTo: target },
      });
    } else {
      // Se já estiver no Dashboard
      if (shouldOpenModal) {
        // Se a intenção é abrir o modal (para "Denunciar um golpe")
        // O Dashboard lerá este estado e abrirá o modal.
        // Não fazemos nada aqui, o Dashboard reage ao state.
        // O `Maps` abaixo serve para limpar o state após o uso.
        navigate(location.pathname, {
          replace: true,
          state: { openReportModal: true, scrollTo: target },
        });
      } else {
        // Para "Minhas denúncias" (onde só queremos rolar)
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
        {/* Este NavLink agora tem a intenção de abrir o modal */}
        <S.NavLink onClick={(e) => handleNavLinkClick("formulario", e, true)}>
          Denunciar um golpe
        </S.NavLink>
        {/* Este NavLink ainda é para rolagem */}
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
