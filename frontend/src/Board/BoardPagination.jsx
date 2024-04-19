import * as React from "react";

import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";

import Pagination from "@mui/material/Pagination";
import { Table, TablePagination } from "@mui/material";

import styles from "./css/Board.module.css";

/**
 * props: totalPageNumbers
 */
export default function BoardPagination(props) {

	const [searchParams, setSearchParams] = useSearchParams();

    return (
        <div className={styles["pagination-wrapper"]}>
            <Pagination
                count={10}
                variant="outlined"
                shape="rounded"
                onChange={(e, currentPage) => {
					setSearchParams({page: currentPage});
                }}
            />
        </div>
    );
}
