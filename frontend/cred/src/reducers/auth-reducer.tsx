import { LOGIN_SUCCESS, LOGIN_FAILED, LOGIN_PROGRESS } from "../constants/action-types";
import { SIGNUP_SUCCESS, SIGNUP_FAILED, SIGNUP_PROGRESS } from "../constants/action-types";
import { LoginState, } from '../models/loginState'
import { SignupState, } from '../models/signupState'
import { USER_TOKEN } from '../constants/store-constants'

const defaultLoginState: LoginState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultSignupState: SignupState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultState = {
    loginState: defaultLoginState,
    signupState: defaultSignupState
};
  
  function authReducer(state = defaultState, action:any) {
    switch(action.type) {
      case LOGIN_SUCCESS:{
        saveUserToken(action.payload)
        const newState = {
          ...state.loginState
        }
        newState.inProgress = false
        newState.success = true
        newState.error = ""
        return {
          ...state,
          loginState: newState
        }
      }
      case LOGIN_FAILED: {
        const newState = {
          ...state.loginState
        }
        newState.inProgress = false
        newState.success = false
        newState.error = action.payload
        return {
          ...state,
          loginState: newState
        }
      }

      case LOGIN_PROGRESS: {
        const newState = {
          ...state.loginState
        }
        newState.inProgress = true
        newState.success = false
        newState.error = ""
        return {
          ...state,
          loginState: newState
        }
      }
      case SIGNUP_SUCCESS:{
        saveUserToken(action.payload)
        const newState = {
          ...state.signupState
        }
        newState.inProgress = false
        newState.success = true
        newState.error = ""
        return {
          ...state,
          signupState: newState
        }
      }
      case SIGNUP_FAILED: {
        const newState = {
          ...state.signupState
        }
        newState.inProgress = false
        newState.success = false
        newState.error = action.payload
        return {
          ...state,
          signupState: newState
        }
      }

      case SIGNUP_PROGRESS: {
        const newState = {
          ...state.signupState
        }
        newState.inProgress = true
        newState.success = false
        newState.error = ""
        return {
          ...state,
          signupState: newState
        }
      }
      default:
      return state;
    }
  };

  const saveUserToken = (response: any) => {
    localStorage.setItem(USER_TOKEN, response.token)
  }
  
  export default authReducer;