import { combineReducers } from "redux";

import loginReducer from './auth-reducer'

export const rootReducer = combineReducers({loginReducer: loginReducer});
export type RootState = ReturnType<typeof rootReducer>