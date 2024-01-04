package com.example.restauratio.utils

class ValidationUtils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            return email.matches(emailPattern.toRegex())
        }

        fun isValidPassword(password: String): Boolean {
            val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
            return password.matches(passwordPattern.toRegex())
        }

        fun isValidName(name: String): Boolean {
            val namePattern = Regex("^[A-Z][a-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*\$")
            return namePattern.matches(name)
        }

        fun isValidAddress(address: String): Boolean {
            val addressPattern = Regex("^\\D+ \\d+(/\\d+)?\$")
            return addressPattern.matches(address)
        }

        fun isValidCity(city: String): Boolean {
            val cityPattern = Regex("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*\$")
            return cityPattern.matches(city)
        }

        fun isValidPostalCode(postalCode: String): Boolean {
            val postalCodePattern = Regex("^\\d{2}-\\d{3}\$")
            return postalCodePattern.matches(postalCode)
        }

        fun isValidPhone(phone: String): Boolean {
            val phonePattern = Regex("^\\+48\\d{9}\$")
            return phonePattern.matches(phone)
        }

        fun validateRegistrationForm(
            email: String,
            password: String,
            passwordAgain: String,
            address: String,
            city: String,
            postalCode: String,
            phone: String,
            firstName: String,
            lastName: String
        ): Boolean {
            return (
                    isValidName(firstName) &&
                            isValidName(lastName) &&
                            isValidEmail(email) &&
                            isValidPassword(password) &&
                            password == passwordAgain &&
                            isValidAddress(address) &&
                            isValidCity(city) &&
                            isValidPostalCode(postalCode) &&
                            isValidPhone(phone)
                    )
        }

        fun validateUserData(
            address: String,
            city: String,
            postalCode: String,
            phone: String,
            firstName: String,
            lastName: String
        ): Boolean {
            return (
                    isValidName(firstName) &&
                            isValidName(lastName) &&
                            isValidAddress(address) &&
                            isValidCity(city) &&
                            isValidPostalCode(postalCode) &&
                            isValidPhone(phone)
                    )
        }

        fun validatePasswordChangeForm(
            password: String,
            passwordAgain: String,
        ): Boolean {
            return (isValidPassword(password) && password == passwordAgain)
        }
    }
}