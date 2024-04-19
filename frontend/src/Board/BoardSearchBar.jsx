import * as React from "react";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import InputLabel from "@mui/material/InputLabel";

import styles from "./css/Board.module.css";

export default function BoardSearchBar() {
    return (
        <>
            <form className={styles["searchbar-wrapper"]} method="post">
                <FormControl
                    sx={{
                        width: "50vw",
                        display: "flex",
                        flexWrap: "wrap",
                        flexDirection: "row",
                        justifyContent: "space-between",
                        alignContent: "center",
                    }}
                >
                    <InputLabel id="search-select">search</InputLabel>
                    <Select
                        labelId="search-select"
                        id="option"
                        name="option"
                        label="Age"
                        size="small"
                        sx={{
                            flexGrow: 1,
                        }}
                    >
                        <MenuItem value={20}>Two</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem>
                    </Select>
                    <TextField
                        id="search-input"
                        name="searchInput"
                        label="searchInput"
                        sx={{
                            marginLeft: "2rem",
                            marginRight: "2rem",
                            flexGrow: 3,
                        }}
                    />
					<button>search</button>
                </FormControl>
            </form>
        </>
    );
}
