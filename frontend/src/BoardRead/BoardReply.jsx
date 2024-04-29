import styles from "./css/BoardRead.module.css";
import { Form, json, redirect } from "react-router-dom";
import { IoPerson } from "react-icons/io5";
import TextField from "@mui/material/TextField";
import {
    Box,
    Button,
    Modal,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from "@mui/material";
import { pageAxios } from "../API/boardAPI";
import { useState } from "react";

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

async function deleteReply(params, replyId) {
    const postId = params.postId;
    const response = await pageAxios.delete(`${postId}/replies/${replyId}`);
    return null;
}

async function modifyReply(params, formData, replyId) {
    const postId = params.postId;
    const memberId = 1;
    const replyText = formData.get("replyText");
    const dto = {
        replyId: replyId,
        postId: postId,
        memberId: memberId,
        replyText: replyText,
    };
    console.log(dto);
    const response = await pageAxios.put(`${postId}/replies/${replyId}`, dto);
    return null;
}

async function writeBoard(formData) {
    const title = formData.get("title");
    const author = formData.get("author");
    const content = formData.get("content");
    const dto = {
        title: title,
        author: author,
        content: content,
    };
    const response = await pageAxios.post("", dto);
    return null;
}

async function modifyBoard(formData, postId) {
    const title = formData.get("title");
    const author = formData.get("author");
    const content = formData.get("content");
    const dto = {
        postId: postId,
        title: title,
        author: author,
        content: content,
    };
    console.log(dto);
    const response = await pageAxios.put(`${postId}`);
}

async function deleteBoard(postId) {
    const response = await pageAxios.delete(`${postId}`);
    return null;
}

export async function replyPostAction({ request, params }) {
    //formData로 nickname(memberId), replyText, postId 전달해야함.
    const formData = await request.formData();
    const obj = formData.get("intent");
    const intent = JSON.parse(obj);
    console.log(intent.intent);

    switch (intent.intent) {
        case "writeBoard":
            try {
                writeBoard(formData);
                return null;
            } catch (e) {}
        case "modifyBoard":
            try {
                modifyBoard(formData, params.postId);
                return null;
            } catch (e) {}
        case "deleteBoard":
            try {
                deleteBoard(params.postId);
                return null;
            } catch (e) {}
        case "previous":
            return redirect("/board");
        case "registerReply":
            try {
                registerReply(params, formData);
                return null;
            } catch (e) {}
            return null;
        case "modifyReply":
            try {
                modifyReply(params, formData, intent.id);

                return null;
            } catch (e) {}
        case "deleteReply":
            try {
                deleteReply(params, intent.id);
                window.location.reload();
                //return redirect(`/board/${params.postId}`);
            } catch (e) {}
            return null;
        default:
            return null;
    }
}

function ReplyForm() {
    const handleRefresh = () => {
        window.location.reload;
    };
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
                value={JSON.stringify({ intent: "registerReply", id: null })}
                type="submit"
                variant="contained"
                onClick={handleRefresh}
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
    const [openModal, setOpenModal] = useState(false);
    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);
    const handleRefresh = () => window.location.reload();
    const [openDeleteModal, setOpenDeleteModal] = useState(false);
    const handleOpenDeleteModal = () => setOpenDeleteModal(true);
    const handleCloseDeleteModal = () => setOpenDeleteModal(false);

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
            <div className={styles["reply-delete-button-wrapper"]}>
                <Button
                    onClick={handleOpenModal}
                    type="text"
                    variant="contained"
                    sx={{
                        marginTop: "2rem",
                        marginRight: "1rem",
                        width: "20%",
                        alignSelf: "end",
                    }}
                >
                    Modify
                </Button>
                <Modal open={openModal} onClose={handleCloseModal}>
                    <div className={styles["reply-modal-modify"]}>
                        <h3>Modify Reply</h3>
                        <Form className={styles["form"]} method="post">
                            <textarea name="replyText"></textarea>
                            <div className={styles["button-wrapper"]}>
                                <button
                                    type="submit"
                                    name="intent"
                                    value={JSON.stringify({
                                        intent: "modifyReply",
                                        id: reply.replyId,
                                    })}
                                    onClick={handleRefresh}
                                    //onClick={handleCloseModify}
                                >
                                    Modify
                                </button>
                                <button
                                    type="button"
                                    onClick={handleCloseModal}
                                >
                                    Close
                                </button>
                            </div>
                        </Form>
                    </div>
                </Modal>
                <Button
                    type="text"
                    variant="contained"
                    onClick={handleOpenDeleteModal}
                    sx={{
                        marginTop: "2rem",
                        width: "20%",
                        alignSelf: "end",
                    }}
                >
                    Delete 1
                </Button>
                <Dialog open={openDeleteModal} onClose={handleCloseDeleteModal}>
                    <DialogTitle>{"Sure to Delete?"}</DialogTitle>
                    <DialogActions>
                        <Button onClick={handleCloseDeleteModal}>Close</Button>
                        <Form method="post">
                            <Button
                                type="submit"
                                variant="contained"
                                name="intent"
                                value={JSON.stringify({
                                    intent: "deleteReply",
                                    id: reply.replyId,
                                })}
                            >
                                Delete
                            </Button>
                        </Form>
                    </DialogActions>
                </Dialog>
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
