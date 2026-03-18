import axios from "axios";

export const registerApi = axios.create({
    baseURL: "http://localhost:8082",
});

export const loginApi = axios.create({
    baseURL: "http://localhost:8081",
});

export const register = (email: string, password: string) => {
    // console.log({ email, password });
    return registerApi.post("/api/register", { email, password });
}


export const login = (email: string, password: string) =>
    loginApi.post("/api/login", { email, password });