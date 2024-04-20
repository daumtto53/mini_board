import styles from "./css/BoardWrite.module.css";
import TextField from "@mui/material/TextField";
import TextareaAutosize from "@mui/material/TextareaAutosize";
import Button from "@mui/material/Button";


/**
 *
 * Deprecated. Usage of BoardForm recommended.
 */
export default function BoardWriteForm() {
    return (
        <form className={styles["write-form"]} method="post">
            <div className={styles.title}>
                <dl>
                    <dt>title</dt>
                    <dd>
                        <TextField
                            variant="outlined"
                            id="board-write-title"
                            label="title"
                            name="title"
                            sx={{
                                width: "85vh",
                                minWidth: "50px",
                                verticalAlign: "baseline",
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <div className={styles.author}>
                <dl>
                    <dt>author</dt>
                    <dd>
                        <TextField
                            variant="filled"
                            id="board-write-author"
                            label="author"
                            name="author"
                            disabled={true}
                            sx={{
                                width: "85vh",
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
                            id="board-write-content"
                            label="content"
                            name="content"
                            sx={{
                                width: "85vh",
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <div className={styles.submit}>
                <div className={styles["button-position"]}>
                    <Button
                        type="submit"
                        variant="contained"
                        sx={{
                            height: "3rem",
                            width: "10vh",
							m: "0 4rem"
                        }}
                    >
                        Submit
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
        </form>
    );
}
