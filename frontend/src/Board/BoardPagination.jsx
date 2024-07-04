import * as React from "react";

import { useSearchParams } from "react-router-dom";

import Pagination from "@mui/material/Pagination";

import styles from "./css/Board.module.css";

/**
 * props: totalPageNumbers
 */
export default function BoardPagination(props) {
    const [searchParams, setSearchParams] = useSearchParams();
    console.log(searchParams.get("pageNum"));
    console.log(searchParams.get("option"));
    console.log(searchParams.get("searchQuery"));

    return (
        <div className={styles["pagination-wrapper"]}>
            <Pagination
                count={props.totalPageSize}
                variant="outlined"
                shape="rounded"
                defaultPage={1}
                onChange={(e, currentPage) => {
                    setSearchParams({
                        pageNum: currentPage,
                        option: searchParams.get("option"),
                        searchQuery: searchParams.get("searchQuery"),
                    });
                }}
            />
        </div>
    );
}
