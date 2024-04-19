import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";

import { pageAxios } from "../API/boardAPI.js";

import styles from "./css/Board.module.css";

//export function BoardRegisterButton() {
//    return (
//        <Button
//            variant="contained"
//            sx={{
//                maxWidth: "15vh",
//                height: "5vh",
//                marginTop: "1vh",
//                marginRight: "5vh",
//                marginLeft: "auto",
//            }}
//        >
//            Register
//        </Button>
//    );
//}

/**
 * data: id, title, author, date, views
 */
export async function boardLoader({ request }) {
    const config = {
        //params: { page: 1 },
    };
    try {
        //const response = await pageAxios.get("/1", config);
        const response = await pageAxios.get("", config);
		console.log(response);
        return response.data;
    } catch (error) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
    }
}

export default function BoardHome() {
    return (
        <section className={styles["board-wrapper"]}>
            <div className={styles.board}>
                <BoardTable />
            </div>
            <BoardSearchBar />
            <button className={styles.register}>Register</button>
            {/*<BoardRegisterButton />*/}
            <BoardPagination className={styles.pagination} />
        </section>
    );
}
