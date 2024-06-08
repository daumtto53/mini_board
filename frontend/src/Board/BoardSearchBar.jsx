import * as React from "react";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import InputLabel from "@mui/material/InputLabel";
import { useState } from "react";
import {Form} from "react-router-dom";
import styles from "./css/Board.module.css";

export default function BoardSearchBar() {
    const [searchQuery, setSearchQuery] = useState("");
	const [searchQueryType, setSearchQueryType] = useState("");

	// should use GET:loader() instead of POST:action()
    return (
        <>
            <Form
                className={styles["searchbar-wrapper"]}
				method="get"
            >
                <FormControl
                    sx={{
                        width: "100%",
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
                        defaultValue={"title"}
                        sx={{
                            flexGrow: 1,
                        }}
                    >
                        <MenuItem value={"title"}>Title</MenuItem>
                        <MenuItem value={"content"}>Content</MenuItem>
                        <MenuItem value={"title_content"}>
                            Title + Content
                        </MenuItem>
                    </Select>
                    <TextField
                        id="search-input"
                        name="searchQuery"
                        label="search"
						value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        sx={{
                            marginLeft: "2rem",
                            marginRight: "2rem",
                            flexGrow: 3,
                        }}
                    />
                    <button type="submit">search</button>
                    {/*<button type="submit" name="intent" value="searchQuery">search</button>*/}
                </FormControl>
            </Form>
        </>
    );
}
