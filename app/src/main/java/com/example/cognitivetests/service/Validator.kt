package com.example.cognitivetests.service

import android.util.Patterns

/**
 * Service for validating inputs.
 */
class Validator {

    /**
     * Checks if the input is not blank.
     *
     * @param input The input to check.
     * @return True if the input is not blank, false otherwise.
     */
    fun isNotBlank(input: String): Boolean {
        return input.isNotBlank()
    }

    /**
     * Checks if the input is not zero.
     *
     * @param input The input to check.
     * @return True if the input is not zero, false otherwise.
     */
    fun isNotZero(input: Double): Boolean {
        return input != 0.0
    }

    /**
     * Checks if the input is in the collection.
     *
     * @param input The input to check.
     * @param collection The collection to check in.
     * @return True if the input is in the collection, false otherwise.
     */
    fun isInCollection(input: String, collection: List<String>): Boolean {
        return collection.contains(input)
    }

    /**
     * Checks if the email is valid.
     *
     * @param email The email to check.
     * @return True if the email is valid, false otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Checks if the password is valid.
     *
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.contains(Regex("[0-9]")) && containsSpecialCharacter(password)
    }

    /**
     * Checks if the confirm password is valid.
     *
     * @param password The password to check.
     * @param confirmPassword The confirm password to check.
     * @return True if the confirm password is valid, false otherwise.
     */
    fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * Checks if the input contains a special character.
     *
     * @param input The input to check.
     * @return True if the input contains a special character, false otherwise.
     */
    private fun containsSpecialCharacter(input: String): Boolean {
        val specialCharPattern = Regex("[^A-Za-z0-9 ]")
        return specialCharPattern.containsMatchIn(input)
    }
}