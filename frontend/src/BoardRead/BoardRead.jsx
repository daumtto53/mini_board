import BoardReadForm from "./BoardReadForm";
import BoardReply from "./BoardReply";

import styles from "./css/BoardRead.module.css";

export default function BoardRead() {
    return (
		<div className={styles["board-read-wrapper"]}>
            <div className={styles["board-content-container"]}>
                <header>
                    <h1>Read on Board</h1>
                </header>
                <main>
                    <section>
                        <BoardReadForm />
                    </section>
                </main>
            </div>
            <div className={styles["board-reply-container"]}>
					<p id="reply_header">reply</p>
					<p id="reply_count">reply_count: 20</p>
				<section>
					<BoardReply />
				</section>
			</div>
		</div>
    );
}
