import { FETCH_CARDS_PROGRESS, FETCH_CARDS_SUCCESS, FETCH_CARDS_FAILED } from "../constants/action-types";
import { ADD_CARD_PROGRESS, ADD_CARD_SUCCESS, ADD_CARD_FAILED } from "../constants/action-types";
import { GetAllCardsState } from '../models/cardState';
import { AddCardState } from '../models/cardState';

const defaultGetAllCardsState: GetAllCardsState = {
    inProgress: false,
    success: false,
    error: "",
    cards: []
}

const defaultAddCardState: AddCardState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultState = {
    getAllCardsState: defaultGetAllCardsState,
    addCardState: defaultAddCardState
}

function cardReducer(state = defaultState, action:any) {
    switch(action.type) {
        case FETCH_CARDS_PROGRESS: {
            const newState = {
                ...state.getAllCardsState
              }
              newState.inProgress = true
              newState.success = false
              newState.error = ""
              newState.cards = []
              return {
                ...state,
                getAllCardsState: newState
              }
        }
        case FETCH_CARDS_SUCCESS: {
            const newState = {
                ...state.getAllCardsState
              }
              newState.inProgress = false
              newState.success = true
              newState.error = ""
              newState.cards = action.payload
              return {
                ...state,
                getAllCardsState: newState
              }
        }
        case FETCH_CARDS_FAILED: {
            const newState = {
                ...state.getAllCardsState
              }
              newState.inProgress = false
              newState.success = false
              newState.error = action.payload
              newState.cards = []
              return {
                ...state,
                getAllCardsState: newState
              }
        }
        case ADD_CARD_PROGRESS: {
          const newState = {
              ...state.addCardState
            }
            newState.inProgress = true
            newState.success = false
            newState.error = ""
            return {
              ...state,
              addCardState: newState
            }
      }
      case ADD_CARD_SUCCESS: {
          const newState = {
              ...state.addCardState
            }
            newState.inProgress = false
            newState.success = true
            newState.error = ""
            return {
              ...state,
              addCardState: newState
            }
      }
      case ADD_CARD_FAILED: {
          const newState = {
              ...state.addCardState
            }
            newState.inProgress = false
            newState.success = false
            newState.error = action.payload
            return {
              ...state,
              addCardState: newState
            }
      }
        default:
        return state;
    }
}

export default cardReducer;