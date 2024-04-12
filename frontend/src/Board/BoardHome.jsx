import BoardTable from "./BoardTable.jsx";
import BoardPagination from "./BoardPagination.jsx";
import BoardSearchBar from "./BoardSearchBar.jsx";

import styles from "./css/Board.module.css";

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
                </section>
            </main>
            <footer></footer>
        </div>
    );
}

export default BoardHome;
