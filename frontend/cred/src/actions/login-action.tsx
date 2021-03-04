import { LOGIN_SUCCESS, LOGIN_FAILED, LOGIN_PROGRESS } from "../constants/action-types";
import { loginApi } from '../services/login-service'

export function login(payload:any) {
    return function (dispatch:any) {
        dispatch({type:LOGIN_PROGRESS, payload:""})
        loginApi(payload, (response: any) => {
            dispatch({type:LOGIN_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:LOGIN_FAILED, payload:error.message})
        })
    }
};