import Axios from "axios";

const axiosInstance = Axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
        Accept: "*/*",
    },
    withCredentials: true
});

export default axiosInstance;