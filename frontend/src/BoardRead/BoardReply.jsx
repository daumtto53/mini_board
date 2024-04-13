import styles from "./css/BoardRead.module.css";

function ReplyForm() {
    return (
        <form className={styles["reply-register-outer-wrapper"]}>
            <div className={styles["reply-register-inner-wrapper"]}>
                <div className={styles["register-id"]}>
                    <input type="text" /> ID
                </div>
                <div className={styles["register-textfield"]}>
                    <input type="text" />
                    {/*<input type='text' className={styles.test} /> TextField*/}
                </div>
            </div>
            <button>post comment</button>
        </form>
    );
}

function ReplyBox() {
    return (
        <div className={styles.replybox}>
            <div className={styles["profile-icon"]}></div>
            <div className={styles.id}>
                <input type="text" />
            </div>
			<div className={styles.timestamp}>
				{/*<input type="text" />*/}
				asdf
			</div>
            <div className={styles.reply}>
                <input type="textarea" />
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
