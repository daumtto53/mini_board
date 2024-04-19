import * as React from 'react';
import Pagination from '@mui/material/Pagination';

import styles from './css/Board.module.css';

export default function BoardPagination() {
	return (
		<div className={styles["pagination-wrapper"]}>
			<Pagination count={10} variant="outlined" shape="rounded"/>
		</div>
	);
}
