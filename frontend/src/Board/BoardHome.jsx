import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";
import Button from "@mui/material/Button";

import styles from "./css/Board.module.css";

function BoardRegisterButton() {
    return <Button
		variant="contained"
		sx={{
			maxWidth: "15vh",
			height: "5vh",
			marginTop:"1vh",
			marginRight: "5vh",
			marginLeft: "auto"
		}}>Register</Button>;
}

function BoardHome() {
    return (
        <div className={styles["page-wrapper"]}>
            <header>
                <h1>Board</h1>
            </header>
            <main>
                <section className={styles["board-wrapper"]}>
                    <div className={styles.board}>
                        <BoardTable />
                    </div>

                    <BoardSearchBar />

                    <BoardPagination className={styles.pagination} />
                    <BoardRegisterButton />
                </section>
            </main>
            <footer></footer>
        </div>
    );
}

export default BoardHome;
