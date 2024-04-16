import BoardWriteForm from "./BoardWriteForm";

import styles from "./css/BoardWrite.module.css";

export default function BoardWrite() {
    return (
        <div className={styles["board-write-container"]}>
            <main>
				<section>
					<BoardWriteForm />
				</section>
			</main>
        </div>
    );
}
