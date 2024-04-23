import { isRouteErrorResponse, useRouteError } from "react-router-dom";
import styles from "./RootErrorBoundary.module.css";

export default function RootErrorBoundary() {
    const error = useRouteError();
	console.log(error);



    return (
        <div className={styles['root-error-boundary-wrapper']}>
            <h1>Oops! Something Went Wrong</h1>
            <h2>error {error.code}: {error.message}</h2>
        </div>
    );
}
