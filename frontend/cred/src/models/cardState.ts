import { CreditCard } from "./creditCard";

export interface GetAllCardsState{
    inProgress: boolean,
    success: boolean,
    error: any,
    cards: Array<CreditCard>
}

export interface AddCardState{
    inProgress: boolean,
    success: boolean,
    error: any
}