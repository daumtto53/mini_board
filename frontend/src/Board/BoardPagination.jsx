import * as React from 'react';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

import styles from './css/Board.module.css';


export default function BoardPagination() {
	return (
		<div className={styles.pagination}>
			<Pagination count={10} variant="outlined" shape="rounded"/>
		</div>
	);
}
