import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import Navigator from "./Navigator.jsx";
import { GoogleOAuthProvider } from "@react-oauth/google";

ReactDOM.createRoot(document.getElementById("root")).render(
    //<React.StrictMode>
	//<GoogleOAuthProvider clientId={import.meta.env.REACT_APP_GOOGLE_API_TOKEN_ID}>
	<GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_API_TOKEN_ID}>
        <Navigator />
	</GoogleOAuthProvider>
    //</React.StrictMode>
);
