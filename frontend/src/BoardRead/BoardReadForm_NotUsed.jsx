import styles from "./css/BoardRead.module.css";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

/**
 *
 * Deprecated. Usage of BoardForm recommended.
 */
function BoardButtons() {
    return (
        <div className={styles.submit}>
            <div>
                <Button
                    type="submit"
                    variant="contained"
                    sx={{
                        height: "3rem",
                        width: "10vh",
                        m: "0 1rem 0 0",
                    }}
                >
                    Submit
                </Button>
                <Button
                    type="text"
                    variant="contained"
                    sx={{
                        height: "3rem",
                        width: "10vh",
                        m: "0 1rem 0 0",
                    }}
                >
                    Modify
                </Button>
                <Button
                    type="text"
                    variant="outlined"
                    sx={{
                        height: "3rem",
                        p: "0 5rem",
                        width: "10vh",
                    }}
                >
                    Previous Board
                </Button>
            </div>
        </div>
    );
}

export default function BoardReadForm() {
    return (
        <form className={styles["content-form"]} method="post">
            <div className={styles.title}>
                <dl>
                    <dt>title</dt>
                    <dd>
                        <TextField
                            variant="outlined"
                            id="board-content-title"
                            name="title"
                            disabled={true}
                            sx={{
                                width: "30vw",
                                minWidth: "50px",
                                verticalAlign: "baseline",
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <div className={styles.misc}>
                <dl>
                    <dt className={styles["views-key"]}>views</dt>
                    <dd className={styles["views-value"]}>123131</dd>
                    <dt className={styles["register-date-key"]}>12312</dt>
                    <dd className={styles["register-date-value"]}>12312</dd>
                </dl>
            </div>
            <div className={styles.author}>
                <dl>
                    <dt>author</dt>
                    <dd>
                        <TextField
                            variant="outlined"
                            id="board-content-author"
                            name="author"
                            disabled={true}
                            sx={{
                                width: "30vw",
                                minWidth: "40px",
                                verticalAlign: "baseline",
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <div className={styles.content}>
                <dl>
                    <dt>content</dt>
                    <dd>
                        <TextField
                            multiline={true}
                            rows={8}
                            variant="outlined"
                            id="board-content-content"
                            name="content"
                            value="13123121312312312312312123121231231233"
                            disabled={true}
                            sx={{
                                width: "30vw",
                                input: {
                                    color: "black",
                                },
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <BoardButtons />
        </form>
    );
}
