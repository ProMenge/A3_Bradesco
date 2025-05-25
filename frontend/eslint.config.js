import configPrettier from "eslint-config-prettier";
import prettier from "eslint-plugin-prettier";
import pluginReact from "eslint-plugin-react";
import globals from "globals";
import tseslint from "typescript-eslint";

export default [
  {
    files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
    languageOptions: { globals: globals.browser },
    rules: {
      // Prettier como regra de erro
      "prettier/prettier": "error",
    },
  },
  ...tseslint.configs.recommended,
  pluginReact.configs.flat.recommended,
  {
    // Configuração do Prettier para desativar regras conflitantes
    plugins: { prettier },
    rules: configPrettier.rules,
    "react/react-in-jsx-scope": "off",
  },
];
