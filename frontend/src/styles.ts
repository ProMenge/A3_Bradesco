import { createGlobalStyle } from "styled-components";

export const colors = {
  white: "#eee",
  black: "#111",
  gray: "#333",
  lightGray: "#A3A3A3",
  green: "#10AC84",
  darkPurple: "#8E44AD",
  red: "#FF3B3B",
  blue: "#003791",
  darkGray: "#4D4D4D",
  orange: "#FF7F0E",
  purple: "#8B008B",
  teal: "#008080",
  darkGreen: "#006400",
  yellow: "#FFD700",
  lightBlue: "#1E90FF",
  steamBlue: "#1b2838",
  amethyst: "#9B59B6",
};

export const breakpoints = {
  desktops: "1024px",
  tablets: "768px",
};

export const GlobalCss = createGlobalStyle`
  *{
    margin: 0;
    padding:0;
    box-sizing: border-box;
    font-family: Roboto, sans-serif;
    list-style: none;
  }
  body {
    background-color: ${colors.black};
    color: ${colors.white};
    padding-top: 40px;
  }

  .container{
    max-width: 1024px;
    width: 100%;
    margin: 0 auto;

    @media (max-width: ${breakpoints.desktops}) {
      max-width: 80%
    }
  }
`;
