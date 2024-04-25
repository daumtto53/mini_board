import styles from "./css/BoardRead.module.css";
import { Form, redirect } from "react-router-dom";
import { IoPerson } from "react-icons/io5";
import TextField from "@mui/material/TextField";
import { Button } from "@mui/material";
import { pageAxios } from "../API/boardAPI";

async function registerReply(params, formData) {
    const postId = params.postId;
    const memberId = 1;
    const replyText = formData.get("replyText");
    const dto = {
        postId: postId,
        memberId: memberId,
        replyText: replyText,
    };
    const response = await pageAxios.post(`/${postId}/replies`, dto);
    return null;
}

export async function replyPostAction({ request, params }) {
    //formData로 nickname(memberId), replyText, postId 전달해야함.
    const formData = await request.formData();
    const intent = formData.get("intent");
    console.log(intent);

    switch (intent) {
        case "submit":
            return null;
        case "modify":
            return null;
        case "previous":
            return redirect("/board");
        case "registerReply":
            try {
                registerReply(params, formData);
                return null;
            } catch (e) {}
            return null;
    }
}

function ReplyForm() {
    return (
        <Form className={styles["reply-form-outer-wrapper"]} method="post">
            <div className={styles["reply-form-inner-wrapper"]}>
                <div className={styles["id-cell"]}>
                    <input
                        id="nickname"
                        className={styles["reply-form-id-box"]}
                        type="text"
                        name="nickname"
                    />
                </div>
                <div className={styles["textarea-cell"]}>
                    <TextField
                        id="replyText"
                        name="replyText"
                        multiline={true}
                        rows={3}
                        sx={{
                            width: "100%",
                            height: "100%",
                            justifyContent: "stretch",
                        }}
                    />

                    {/*<textarea className={styles["reply-form-textarea"]}/>*/}

                    {/*<input type='text' className={styles.test} /> TextField*/}
                </div>
            </div>
            <Button
                name="intent"
                value="registerReply"
                type="submit"
                variant="contained"
                sx={{
                    marginTop: "2rem",
                    width: "20%",
                    alignSelf: "end",
                }}
            >
                Post Reply
            </Button>
        </Form>
    );
}

function ReplyBox(props) {
    const reply = props.reply;

    return (
        <ul>
            <div className={styles.replybox}>
                <div className={styles["profile-icon"]}>
                    <IoPerson className={styles.icon} />
                </div>
                <div className={styles.id}>ID: {reply.replyAuthor}</div>
                <div className={styles.timestamp}>{reply.replyUpdatedAt}</div>
                <div className={styles.reply}>
                    {/* duplicatte element id */}
                    <TextField
                        id="reply-textfield"
                        variant="outlined"
                        disabled={true}
                        multiline={true}
                        rows={3}
                        value={reply.replyText}
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                                WebkitTextFillColor: "#000000",
                            },
                            overflowY: "auto",
                            width: "100%",
                            height: "100%",
                            padding: "1rem",
                        }}
                    />
                </div>
            </div>
        </ul>
    );
}

export default function BoardReply(props) {
    const replies = props.replies;

    const replyList = replies.map((reply) => {
        return <ReplyBox key={reply.replyId} reply={reply} />;
    });
    return (
        <div className={styles["reply-wrapper"]}>
            <div className={styles["reply-list"]}>{replyList}</div>

            <ReplyForm />
        </div>
    );
}
