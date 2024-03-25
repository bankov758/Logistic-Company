"use server";

import Axios from "axios";
import { cookies } from "next/headers";

const jsession = cookies().get("JSESSIONID")

const axiosInstance = Axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
        Accept: "*/*"
    },
    withCredentials: true
});

if( jsession ) {
    axiosInstance.defaults.headers.common["Cookie"] = "JSESSIONID=" + jsession.value;
}

export default axiosInstance;