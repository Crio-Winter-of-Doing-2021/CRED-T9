import { CreditCard } from "./creditCard";
import { Transaction } from "./transaction";

interface BaseState {
    inProgress: boolean,
    success: boolean,
    error: any
}

export interface GetAllCardsState extends BaseState{
    cards: Array<CreditCard>
}

export interface AddCardState extends BaseState{
}

export interface GetStatementState extends BaseState{
    statement: Array<Transaction>
}

export interface PayBillState extends BaseState {
    
}