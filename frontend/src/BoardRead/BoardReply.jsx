import styles from "./css/BoardRead.module.css";
import { IoPerson } from "react-icons/io5";
import TextField from "@mui/material/TextField";
import { Button } from "@mui/material";

function ReplyForm() {
    return (
        <form className={styles["reply-form-outer-wrapper"]}>
            <div className={styles["reply-form-inner-wrapper"]}>
                <div className={styles["id-cell"]}>
                    <input disabled className={styles["reply-form-id-box"]} type="text" />
                </div>
                <div className={styles["textarea-cell"]}>
					<TextField
						name="reply-content"
						multiline={true}
						rows={3}
						sx={{
							width:"100%",
							height:"100%",
							justifyContent:"stretch"
						} }
					/>

					{/*<textarea className={styles["reply-form-textarea"]}/>*/}

                    {/*<input type='text' className={styles.test} /> TextField*/}
                </div>
            </div>
				 <Button
				 	type="submit"
				 	variant="contained"
					sx={{
						marginTop:"2rem"
					}}
				 >Post Reply</Button>
        </form>
    );
}

function ReplyBox() {
    return (
        <div className={styles.replybox}>
            <div className={styles["profile-icon"]}>
                <IoPerson className={styles.icon} />
            </div>
            <div className={styles.id}>ID</div>
            <div className={styles.timestamp}>Time</div>
            <div className={styles.reply}>
				{/* duplicatte element id */}
                <TextField
					id="reply-textfield"
					variant="outlined"
					disabled={true}
					multiline={true}
					rows={3}
					value="12313
					123
					123"
					sx={{
						overflowY: "auto",
						width:"100%",
						height:"100%",
						padding:"1rem",
					}}
				/>
            </div>
        </div>
    );
}

export default function BoardReply() {
    return (
        <div className={styles["reply-wrapper"]}>
            <div className={styles["reply-list"]}>
                <ReplyBox />
                <ReplyBox />
                <ReplyBox />
                <ReplyBox />
                <ReplyBox />
                <ReplyBox />
            </div>

            <ReplyForm />
        </div>
    );
}
