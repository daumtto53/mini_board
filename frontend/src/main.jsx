import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import BoardRead from "./BoardRead/BoardRead.jsx";
import Navigator from "./Navigator.jsx";
import Base from "./RootLayout.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <Navigator />
    </React.StrictMode>
);
