import styles from "./RootLayout.module.css";
import banner from "../resources/banner.jpg";
import { Link, NavLink, Outlet } from "react-router-dom";

export default function RootLayout() {
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
                        </ul>
                </div>
            </header>
			<main>
				<Outlet />
			</main>
            <div className={styles.main}></div>
        </div>
    );
}
