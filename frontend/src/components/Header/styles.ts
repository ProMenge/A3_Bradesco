import styled from "styled-components";

interface NavProps {
  open: boolean;
}

export const Container = styled.header`
  width: 100%;
  padding: 1.5rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 1280px;
  margin: 0 auto;
`;

export const LogoWrapper = styled.div`
  img {
    height: 50px;
  }
`;

export const Nav = styled.nav`
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-left: auto;
`;

export const NavLink = styled.a`
  text-decoration: none;
  font-weight: 500;
  color: #222;
  font-size: 1.2rem;
  position: relative;
  cursor: pointer;

  &:hover {
    color: #2ab280;
  }

  &::after {
    content: "";
    display: block;
    width: 0%;
    height: 2px;
    background-color: #2ab280;
    transition: width 0.3s;
    margin-top: 4px;
  }

  &:hover::after {
    width: 100%;
  }
`;

export const LogoutButton = styled.button`
  margin-left: 2rem;
  background: transparent;
  border: 1px solid #333;
  padding: 0.5rem 1.5rem;
  border-radius: 6px;
  font-weight: 500;
  font-size: 01.2rem;
  cursor: pointer;
  transition: all 0.2s ease-in-out;

  &:hover {
    background-color: #333;
    color: #fff;
  }
`;

export const Hamburger = styled.button`
  display: none;
  font-size: 2rem;
  background: none;
  border: none;
  cursor: pointer;
  margin-left: auto;

  @media (max-width: 768px) {
    display: block;
  }
`;

export const Navi = styled.nav<NavProps>`
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-left: auto;

  @media (max-width: 768px) {
    flex-direction: column;
    position: absolute;
    top: 90px;
    right: 2rem;
    background: white;
    padding: 1rem 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    display: ${({ open }) => (open ? "flex" : "none")};
  }
`;
