import BoardForm from "../BoardCommonComponents/BoardForm";

import styles from "./css/BoardWrite.module.css";

export default function BoardWrite() {
    return (
        <div className={styles["board-write-wrapper"]}>
            <div className={styles["board-write-container"]}>
                <main>
                    <section>
                        <BoardForm mode={"write"} />
                    </section>
                </main>
            </div>
        </div>
    );
}
