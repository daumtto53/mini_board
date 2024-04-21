import { isRouteErrorResponse, useRouteError } from "react-router-dom";
import styles from "./RootErrorBoundary.module.css";

export default function RootErrorBoundary() {
    const error = useRouteError();
	console.log(error);

	let errorMessage = error.data;

    return (
        <div className={styles['root-error-boundary-wrapper']}>
            <h1>Oops! Something Went Wrong</h1>
            <h2>error {error.status}: {errorMessage}</h2>
        </div>
    );
}
