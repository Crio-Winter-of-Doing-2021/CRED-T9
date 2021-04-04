import axios from "axios"
import { USER_TOKEN } from '../constants/store-constants'

const baseUrl = process.env.REACT_APP_API_BASE_URL

export function getAllCardsApi(successCallback: (response: any) => any, 
  errorCallback: (errorCallback: any) => any) {
  axios.get(baseUrl + "cards", getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

export function addCardApi(payload: any, successCallback: (response: any) => any, 
  errorCallback: (errorCallback: any) => any) {
  axios.post(baseUrl + "cards", payload, getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

export function getStatementApi(id: number, year: number, month: number,
  successCallback: (response: any) => any, errorCallback: (errorCallback: any) => any) {
  axios.get(baseUrl + "cards/" + id + "/statements/" + year + "/" + month, getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

export function payBillApi(id: string, payload: any, 
  successCallback: (response: any) => any, errorCallback: (errorCallback: any) => any) {
  axios.post(baseUrl + "cards/" + id + "/pay", payload, getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

function handleError(error: any, errorCallback: (errorCallback: any) => any) {
  if (error.response) {
    errorCallback(error.response.data)
  } else {
    errorCallback(error)
  }
}

function getConfig() {
  return { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(USER_TOKEN) } }
}