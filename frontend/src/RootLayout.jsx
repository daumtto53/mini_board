import styles from "./RootLayout.module.css";
import banner from "../resources/banner.jpg";
import { Link, NavLink, Outlet } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function Footer() {
    return <></>;
}

const handleLogin = () => {
	window.location.href = `http://localhost:8080/${import.meta.env.VITE_LOGIN_API_NAVER}`
}

export default function RootLayout() {
    //Login
    const [user, setUser] = useState([]);


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
							<Link onClick={() => {handleLogin()}}>Login</Link>
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
