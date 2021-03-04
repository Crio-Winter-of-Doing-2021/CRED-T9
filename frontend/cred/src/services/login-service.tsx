import axios from "axios"

export function loginApi(payload:any, success:(response:any) => any, error:(error:any) => any) {
    axios.post("http://localhost:8000/login", payload)
        .then(res => success(res))
        .catch(err => error(err))
}