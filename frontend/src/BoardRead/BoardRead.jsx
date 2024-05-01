//import BoardReadForm from "./BoardReadForm";
import BoardReply from "./BoardReply";
import BoardForm from "../BoardCommonComponents/BoardForm";

import { pageAxios } from "../API/boardAPI";

import styles from "./css/BoardRead.module.css";
import { useLoaderData } from "react-router-dom";

export async function boardReadLoader({ request, params }) {
    const postId = params.postId;

    try {
        const response = await pageAxios.get(`/${postId}`);
        return {
            responseData: response.data,
            postId: postId,
        };
    } catch (error) {
        throw error;
    }
}

export default function BoardRead() {
    const loaderData = useLoaderData();
    const responseData = loaderData["responseData"];
    const postId = loaderData.postId;
    const { boardReadReplyDTOList, ...boardData } = responseData;

    return (
        <div className={styles["board-read-wrapper"]}>
            <div className={styles["board-content-container"]}>
                <header></header>
                <main>
                    <section>
                        <BoardForm
                            postId={postId}
                            boardData={boardData}
                            mode={"read"}
                        />
                    </section>
                </main>
            </div>
            <div className={styles["board-reply-container"]}>
                <p id="reply_count">replies: {boardReadReplyDTOList.length}</p>
                <section>
                    <BoardReply replies={boardReadReplyDTOList} />
                </section>
            </div>
        </div>
    );
}
