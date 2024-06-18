import styles from "./RootLayout.module.css";
import banner from "../resources/banner.jpg";
import { Link, NavLink, Outlet } from "react-router-dom";
import { useState } from "react";
import { useGoogleLogin } from "@react-oauth/google";
import axios from "axios";

function Footer() {
    return <></>;
}

function GoogleLoginLink() {
    const [user, setUser] = useState([]);

    const handleLogin = useGoogleLogin({
        onSuccess: async (tokenResponse) => {
            console.log(tokenResponse);
            //token 받아오는것을 성공했다면 서버에서 JWT 토큰을 받아옴.
			try {
				const response = await axios.post(
                    "http://localhost:8080/login/oauth2/authorization/google",
					{},
					{
						headers: {
							Authorization : `Bearer ${tokenResponse.code}`,
						},
					}
				);
				console.log(response.data);
			} catch (error) {
				console.error("error");
			}
        },
        onError: (error) => {
            console.log("login failed:" + error);
        },
        flow: "auth-code",
    });

    return <Link onClick={() => handleLogin()}>Login</Link>;
}

export default function RootLayout() {
    //Login
    const [user, setUser] = useState([]);
    const handleLogin = useGoogleLogin({
        onSuccess: (codeResponse) => {
            console.log(codeResponse);
        },
        onError: (error) => {
            console.log("login failed:" + error);
        },
        flow: "auth-code",
    });
    return (
        <div className={styles.base}>
            <header>
                <img src={banner} alt="banner" />
                <div className={styles["navbar-wrapper"]}>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="board">Board</Link>
                        </li>
                        <li>
                            <Link to="write">Write</Link>
                        </li>
                        <li>
                            <GoogleLoginLink />
                        </li>
                    </ul>
                </div>
            </header>
            <main>
                <Outlet />
            </main>
            <footer>
                Text
                {/*<Footer />*/}
            </footer>
        </div>
    );
}
