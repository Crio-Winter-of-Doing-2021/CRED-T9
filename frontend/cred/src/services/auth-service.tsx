import axios from "axios"

export function loginApi(payload:any, success:(response:any) => any, error:(error:any) => any) {
    const loginUrl = process.env.REACT_APP_API_BASE_URL + "login";
    axios.post(loginUrl, payload)
        .then(res => success(res))
        .catch(err => error(err))
}

export function signupApi(payload:any, success:(response:any) => any, error:(error:any) => any) {
    const loginUrl = process.env.REACT_APP_API_BASE_URL + "signup";
    axios.post(loginUrl, payload)
        .then(res => success(res))
        .catch(err => error(err))
}