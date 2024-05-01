import styles from "./BoardForm.module.css";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { useNavigate, Form, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { pageAxios } from "../API/boardAPI";

function BoardButtons(props) {
    const NAVIGATE = useNavigate();
    const readMode = props.disableWriting === true ? true : false;
    const postId = props.postId;

    const intentValue =
        props.mode === "write"
            ? JSON.stringify({
                  intent: "writeBoard",
                  id: null,
              })
            : JSON.stringify({
                  intent: "modifyBoard",
                  id: postId,
              });

    return (
        <div className={styles.submit}>
            {!readMode && (
                <Button
                    name="intent"
                    value={intentValue}
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
            {readMode && (
                <Button
                    name="intent"
                    type="button"
                    variant="contained"
                    onClick={() =>
                        NAVIGATE("/write", { state: { postId: postId } })
                    }
                    sx={{
                        height: "3rem",
                        width: "10vh",
                        m: "0 1rem 0 0",
                    }}
                >
                    Modify
                </Button>
            )}
            {readMode && (
                <Button
                    name="intent"
                    value={JSON.stringify({ intent: "deleteBoard", id: null })}
                    type="submit"
                    variant="contained"
                    sx={{
                        height: "3rem",
                        width: "10vh",
                        m: "0 1rem 0 0",
                    }}
                >
                    delete
                </Button>
            )}
            <Button
                name="intent"
                value={JSON.stringify({ intent: "previous", id: null })}
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
    );
}

export default function BoardForm(props) {
    const disableWriting =
        props.mode === "write" || props.mode === "modify" ? false : true;
    const postId = props.postId;

    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [content, setContent] = useState("");
    const [updatedAt, setUpdatedAt] = useState("");
    const [views, setViews] = useState(0);

    const loadBoardData = async (postId) => {
        const response = await pageAxios.get(`/${postId}`);
        const res = response.data;
        return {
            title: res.title,
            author: res.author,
            content: res.content,
            updatedAt: res.updatedAt,
            views: res.views,
        };
    };

    useEffect(() => {
        if (postId !== null) {
            loadBoardData(postId).then((res) => {
                setTitle(res.title);
                setAuthor(res.author);
                setContent(res.content);
                setViews(res.views);
                setUpdatedAt(res.updatedAt);
            });
        }
    }, []);

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
                            value={title}
                            disabled={disableWriting}
                            onChange={(e) => setTitle(e.target.value)}
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
                    <dd className={styles["views-value"]}>{views}</dd>
                    <dt className={styles["register-date-key"]}>DateTime</dt>
                    <dd className={styles["register-date-value"]}>
                        {updatedAt}
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
                            value={author}
                            disabled={disableWriting}
                            onChange={(e) => setAuthor(e.target.value)}
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
                            value={content}
                            disabled={disableWriting}
                            onChange={(e) => setContent(e.target.value)}
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
            <BoardButtons
                postId={postId}
                disableWriting={disableWriting}
                mode={props.mode}
            />
        </Form>
    );
}
