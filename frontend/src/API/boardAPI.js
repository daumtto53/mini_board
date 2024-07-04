import axios from "axios";

export const pageAxios = axios.create({
    baseURL: "http://localhost:8080/board",
	headers: {
		'Content-Type': 'application/json'
	},
	withCredentials: true
});
