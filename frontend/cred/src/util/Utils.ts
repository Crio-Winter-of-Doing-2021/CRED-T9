const emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}"

export function isEmailValid(email: string) {
   return email.match(emailRegex)
}

