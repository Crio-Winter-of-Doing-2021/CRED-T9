import { isValid } from 'cc-validate'

const emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}"
const numberRegex = '^[0-9]*$'

export function isEmailValid(email: string) {
   return email && email.match(emailRegex)
}

export function isMobileNumberValid(mobileNumber: string) {
   return mobileNumber && isNumber(mobileNumber) && mobileNumber.length === 10
}

export function isCardNumberValid(cardNumber: string) {
   let result = isValid(cardNumber)
   return (result as any).isValid
}

export function isCardCvvValid(cardCvv: string) {
   return cardCvv && isNumber(cardCvv)
}

export function isCardExpiryValid(cardExpiry: string) {
   if(!cardExpiry) return false
   let splitarr = cardExpiry.split('/')
   return splitarr.length === 2 && splitarr[0].length === 2 && splitarr[1].length === 4 && 
      isNumber(splitarr[0]) && isNumber(splitarr[1])
}

function isNumber(value: string) {
   return value.match(numberRegex)
}

export function getCardType(cardNumber: string) {
   let result = isValid(cardNumber)
   return (result as any).cardType
}