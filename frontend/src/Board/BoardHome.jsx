import { useLoaderData, useSearchParams } from "react-router-dom";

import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";

import { pageAxios } from "../API/boardAPI.js";

import styles from "./css/Board.module.css";

/**
 * data: id, title, author, date, views
 */
export async function boardLoader({ request }) {

	const url = new URL(request.url);
	let requestPageNum = url.searchParams.get("pageNum");
	requestPageNum = requestPageNum === null ? 1 : requestPageNum;
	console.log(requestPageNum);

    const config = {
        params: {
			pageNum: requestPageNum
		}
    };
    try {
        const response = await pageAxios.get("", config);
        return response.data["dtoList"];
    } catch (error) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
    }
}

export default function BoardHome() {
    const boardList = useLoaderData();

    return (
        <section className={styles["board-wrapper"]}>
            <div className={styles.board}>
                <BoardTable boardList={boardList} />
            </div>
            <BoardSearchBar />
            <button className={styles.register}>Register</button>
            <BoardPagination className={styles.pagination} />
        </section>
    );
}
