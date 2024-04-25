import styles from "./BoardForm.module.css";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { useNavigate, Form } from "react-router-dom";

function BoardButtons(props) {
    const NAVIGATE = useNavigate();
    const readMode = props.disableWriting === true ? true : false;

    return (
        <div className={styles.submit}>
            <div>
                {readMode &&
                    !(
                        <Button
							name="intent"
							value="submit"
                            type="submit"
                            variant="contained"
							defaultValue={"submit"}
                            sx={{
                                height: "3rem",
                                width: "10vh",
                                m: "0 1rem 0 0",
                            }}
                        >
                            Submit
                        </Button>
                    )}
                {readMode &&
                    (
                        <Button
							name="intent"
							value="modify"
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
                    )}
                <Button
					name="intent"
					value="previous"
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

export default function BoardForm(props) {
    const disableWriting = props.mode === "write" ? false : true;
    const boardData = props.boardData;

    return (
        <Form
            className={styles["content-form"]}
            id="boardReadForm"
            method="post"
        >
            <div className={styles.title}>
                <dl>
                    <dt>title</dt>
                    <dd>
                        <TextField
                            variant="outlined"
                            id="board-content-title"
                            name="title"
                            value={boardData.title}
                            disabled={disableWriting}
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                    WebkitTextFillColor: "#000000",
                                },
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
                    <dd className={styles["views-value"]}>{boardData.views}</dd>
                    <dt className={styles["register-date-key"]}>DateTime</dt>
                    <dd className={styles["register-date-value"]}>
                        {boardData.updatedAt}
                    </dd>
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
                            value={boardData.author}
                            disabled={disableWriting}
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                    WebkitTextFillColor: "#000000",
                                },
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
                            value={boardData.content}
                            disabled={disableWriting}
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                    WebkitTextFillColor: "#000000",
                                },
                                width: "30vw",
                                input: {
                                    color: "black",
                                },
                            }}
                        />
                    </dd>
                </dl>
            </div>
            <BoardButtons disableWriting={disableWriting} />
        </Form>
    );
}
