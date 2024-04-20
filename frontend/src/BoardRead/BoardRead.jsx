//import BoardReadForm from "./BoardReadForm";
import BoardReply from "./BoardReply";
import BoardForm from "../BoardCommonComponents/BoardForm";

import { pageAxios } from "../API/boardAPI";

import styles from "./css/BoardRead.module.css";
import { useLoaderData } from "react-router-dom";

export async function boardReadLoader({ request, params }) {
    const pageId = params.pageId;
    console.log("pageId " + pageId);

    try {
        const response = await pageAxios.get(`/${pageId}`);
		console.log(response);
        /**
         * TODO
         * fetch data of title, author, content, time, views, replyList {[author, updated time, text]...}
         */
		return response.data;
    } catch (error) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
    }
}

export default function BoardRead() {

	const responseData = useLoaderData();
	console.log(responseData);
    return (
        <div className={styles["board-read-wrapper"]}>
            <div className={styles["board-content-container"]}>
                <header></header>
                <main>
                    <section>
                        <BoardForm mode={"read"} />
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
