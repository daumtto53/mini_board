import BoardWriteForm from "./BoardWriteForm";

import styles from "./css/BoardWrite.module.css";

export default function BoardWrite() {
    return (
        <div className={styles["board-write-container"]}>
            <header>
				<h1>Write on Board</h1>
			</header>
            <main>
				<section>
					<BoardWriteForm />
				</section>
			</main>
        </div>
    );
}
