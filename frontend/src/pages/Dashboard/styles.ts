import styled from "styled-components";

interface BottomSectionProps {
  background: string;
}

export const Container = styled.section`
  display: flex;
  flex-direction: column;
  height: 100vh;
`;

export const TopSection = styled.div`
  flex: 1;
  background-color: #f5f5f5;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;

  h2 {
    font-size: 2rem;
    margin-bottom: 0.5rem;
  }

  p {
    font-size: 1.4rem;
    color: #555;
    margin-bottom: 2rem;
  }

  button {
    width: 300px;
    height: 50px;
    font-size: 1.5rem;
  }
`;

export const BottomSection = styled.div<BottomSectionProps>`
  flex: 2;
  position: relative;
  background: url(${(props) => props.background}) no-repeat center center;
  background-size: cover;
  padding: 2rem;
  overflow-y: auto;
  z-index: 0;
`;
