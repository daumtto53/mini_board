import { useLoaderData, useNavigate, useSearchParams } from "react-router-dom";

import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";

import { pageAxios } from "../API/boardAPI.js";

import styles from "./css/Board.module.css";
import { useEffect } from "react";

/**
 * data: id, title, author, date, views
 */
export async function boardLoader({ request }) {
    const url = new URL(request.url);
    let requestPageNum = url.searchParams.get("pageNum");
	const option = url.searchParams.get("option");
	const searchQuery = url.searchParams.get("searchQuery");
    requestPageNum = requestPageNum === null ? 1 : requestPageNum;

    const config = {
        params: {
            pageNum: requestPageNum,
			option: option,
			searchQuery: searchQuery
        },
    };
    try {
        const response = await pageAxios.get("", config);
        return {
            dtoList: response.data["dtoList"],
            totalPageSize: response.data["totalPageSize"],
            defaultPageNum: parseInt(requestPageNum),
        };
    } catch (error) {
        throw error;
    }
}


export default function BoardHome() {
    const loaderData = useLoaderData();
	const NAVIGATE = useNavigate();

	useEffect(() => {
		NAVIGATE(".", {replace: true});
	}, []);

    return (
        <section className={styles["board-wrapper"]}>
            <div className={styles.board}>
                <BoardTable boardList={loaderData.dtoList} />
            </div>
            <BoardSearchBar />
            <button className={styles.register}>Register</button>
            <BoardPagination
                className={styles.pagination}
                totalPageSize={loaderData.totalPageSize}
                defaultPageNum={loaderData.defaultPageNum}
            />
        </section>
    );
}
