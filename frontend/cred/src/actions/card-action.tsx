import { FETCH_CARDS_PROGRESS, FETCH_CARDS_SUCCESS, FETCH_CARDS_FAILED } from "../constants/action-types";
import { ADD_CARD_PROGRESS, ADD_CARD_SUCCESS, ADD_CARD_FAILED } from "../constants/action-types";
import { getAllCardsApi, addCardApi } from '../services/card-service'

export function getAllCards() {
    return function (dispatch:any) {
        dispatch({type:FETCH_CARDS_PROGRESS, payload:""})
        getAllCardsApi((response: any) => {
            dispatch({type:FETCH_CARDS_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:FETCH_CARDS_FAILED, payload:error.message})
        })
    }
}

export function addCard(payload: any) {
    return function (dispatch:any) {
        dispatch({type:ADD_CARD_PROGRESS, payload:""})
        addCardApi(payload, (response: any) => {
            dispatch({type:ADD_CARD_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:ADD_CARD_FAILED, payload:error.message})
        })
    }
}