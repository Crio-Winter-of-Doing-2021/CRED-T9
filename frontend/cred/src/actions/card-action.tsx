import { FETCH_CARDS_PROGRESS, FETCH_CARDS_SUCCESS, FETCH_CARDS_FAILED } from "../constants/action-types";
import { getAllCardsApi } from '../services/card-service'

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

