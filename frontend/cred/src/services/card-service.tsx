import axios from "axios"

const baseUrl = process.env.REACT_APP_API_BASE_URL

export function getAllCardsApi(success:(response:any) => any, error:(error:any) => any) {
    axios.get(baseUrl + "cards")
        .then(res => success(res))
        .catch(err => error(err))
}

export function addCardApi(payload: any, success:(response:any) => any, error:(error:any) => any) {
    axios.post(baseUrl + "cards", payload)
        .then(res => success(res))
        .catch(err => error(err))
}