import BoardReadForm from "./BoardReadForm";
import BoardReply from "./BoardReply";

import {pageAxios} from "../API/boardAPI";

import styles from "./css/BoardRead.module.css";

export async function boardReadLoader({request, params}) {
	const pageId = params.pageId;
	console.log("pageId " + pageId);

	try {
		const response = pageAxios.get(`/${pageId}`);
		/**
		 * TODO
		 * fetch data of title, author, content, time, views, replyList {[author, updated time, text]...}
		 */
	}
	catch(error) {
		console.log(error.response.data);
		console.log(error.response.status);
		console.log(error.response.headers);
	}
};

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
