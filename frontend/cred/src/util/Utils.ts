const emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}"

export function isEmailValid(email: string) {
   return email && email.match(emailRegex)
}

export function isMobileNumberValid(mobileNumber: string) {
   return !Number.isNaN(mobileNumber) && mobileNumber.length === 10
}

