import axios from "axios"
import { USER_TOKEN } from '../constants/store-constants'

const baseUrl = process.env.REACT_APP_API_BASE_URL

export function getAllCardsApi(success:(response:any) => any, error:(error:any) => any) {
    axios.get(baseUrl + "cards")
        .then(res => success(res))
        .catch(err => error(err))
}

export function addCardApi(payload: any, success:(response:any) => any, error:(error:any) => any) {
    axios.post(baseUrl + "cards", payload, getConfig())
        .then(res => success(res))
        .catch(err => error(err))
}

export function getStatementApi(id: number, year: number, month: number, 
  success:(response:any) => any, error:(error:any) => any) {
    axios.get(baseUrl + "cards/"+id+"/statements/"+year+"/"+month, getConfig())
        .then(res => success(res))
        .catch(err => error(err))
  }

function getConfig() {
  return { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(USER_TOKEN) } }
}