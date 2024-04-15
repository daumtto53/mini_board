import styles from "./Base.module.css";
import banner from "../resources/banner.jpg";
import { NavLink } from "react-router-dom";

export default function Base() {
    return (
        <div className={styles.base}>
            <header>
                <img src={banner} alt="banner" />
                <div className={styles["navbar-wrapper"]}>
                        <ul>
							<li>
								<a href="123123">Link</a>
							</li>
							<li>
								<a href="123123">Link</a>
							</li>
							<li>
								<a href="123123">Link</a>
							</li>
                        </ul>
                </div>
            </header>
            <div className={styles.main}></div>
        </div>
    );
}
