import BoardForm from "../BoardCommonComponents/BoardForm";

import styles from "./css/BoardWrite.module.css";

/**
 * boardData
 * 		author content title updatedAt views
 */
export default function BoardWrite() {
    const boardData = {
        author: "",
        content: "",
        title: "",
        updatedAt: "",
        views: "",
    };
    return (
        <div className={styles["board-write-wrapper"]}>
            <div className={styles["board-write-container"]}>
                <main>
                    <section>
                        <BoardForm boardData={boardData} mode={"write"} />
                    </section>
                </main>
            </div>
        </div>
    );
}
