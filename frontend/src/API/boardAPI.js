import axios from "axios";

export const pageAxios = axios.create({
    //baseURL: "http://localhost:8080/board",
	baseURL: `${import.meta.env.VITE_BASEURL}/board`,
	headers: {
		'Content-Type': 'application/json'
	},
	withCredentials: true
});
