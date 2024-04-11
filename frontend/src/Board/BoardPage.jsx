import Board from "./Board.jsx";
import BoardPagination from "./BoardPagination.jsx";

import styles from "./css/Board.module.css";

function BoardPage() {
    return (
        <div className={styles["page-wrapper"]}>
            <header>
                <h1>Board</h1>
            </header>
            <main>
                <section className={styles["board-wrapper"]}>
                    <Board className={styles.board}/>
					{/*<BoardSearcher className={styles.searcher}/>*/}
					<BoardPagination className={styles.pagination} />
                </section>
            </main>
            <footer></footer>
        </div>
    );
}

export default BoardPage;
