import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";

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
