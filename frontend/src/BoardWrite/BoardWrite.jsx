import { useLocation } from "react-router-dom";
import BoardForm from "../BoardCommonComponents/BoardForm";
import { useEffect, useState } from "react";

import styles from "./css/BoardWrite.module.css";
import { pageAxios } from "../API/boardAPI";

/**
 * boardData
 * 		author content title updatedAt views
 */

export default function BoardWrite() {
    const loc = useLocation();
	const postId = loc.state === null ? null : loc.state.postId;
	const mode = loc.state === null ? "write" : "modify"

    return (
        <div className={styles["board-write-wrapper"]}>
            <div className={styles["board-write-container"]}>
                <main>
                    <section>
                        <BoardForm postId={postId} mode={mode} />
						<></>
                    </section>
                </main>
            </div>
        </div>
    );
}
