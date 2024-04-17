import axios from 'axios';

export const pageAxios = axios.create({
	baseURL: "https://localhost:8080/board",

})
