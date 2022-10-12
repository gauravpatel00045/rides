package com.rides.validator;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * This class validate all required credentials
 * eg. string length, URL format, pattern matching
 */
public class ValidationClass {

    // variable declaration
    public static final String PLUS_SIGN = "+";
    private static final String TAG = ValidationClass.class.getSimpleName();


    /**
     * This method validate string if it is empty or not
     *
     * @param inputString(String) : user input string
     * @return (boolean) : it return true if string is null or zero length
     * @see <a href="https://developer.android.com/reference/android/text/TextUtils.html#isEmpty%28java.lang.CharSequence%29">isEmpty</a>
     */
    public static boolean isEmpty(String inputString) {
        return TextUtils.isEmpty(inputString);
    }


    /**
     * This method validate string with specific patterns
     * e.g. email,alphaNumericPassword,webUrl
     *
     * @param inputString(String) : user input string
     * @param pattern(String)     : patterns to be matched with inputString
     * @return (boolean) : it return false if inputString does not match with required pattern
     * @see Patterns#EMAIL_ADDRESS
     */
    public static boolean matchPattern(String inputString, String pattern) {
        return !(TextUtils.isEmpty(inputString) || pattern.isEmpty()) && Pattern.matches(pattern, inputString);

    }

    /**
     * This method validate the phone number with specific length
     *
     * @param inputPhoneNumber(String) : user input inputPhoneNumber e.g. 9999999999
     * @param phoneNumberLength(int)   : phone number length e.g. 10
     * @return (boolean) : it return false, if and only if, when inputPhoneNumber length is not equal to phoneNumberLength
     */
    public static boolean checkPhoneNumber(String inputPhoneNumber, int phoneNumberLength) {
        return !(TextUtils.isEmpty(inputPhoneNumber) || phoneNumberLength <= 0) && inputPhoneNumber.trim().length() == phoneNumberLength;
    }

    /**
     * This method check string with minimum length
     *
     * @param inputString(String) : input string
     * @param minLength(int)      :  minimum length e.g. 6
     * @return (boolean) : it return false if input string is less than minLength
     */
    public static boolean checkMinLength(String inputString, int minLength) {
        return !(TextUtils.isEmpty(inputString) || minLength <= 0) && inputString.trim().length() >= minLength;
    }

    /**
     * This method check string with maximum length
     *
     * @param inputString(String) : input string
     * @param maxLength(int)      :  maximum length e.g 10
     * @return (boolean) : it return false if input string is greater than maxLength
     */
    public static boolean checkMaxLength(String inputString, int maxLength) {
        return !(TextUtils.isEmpty(inputString) || maxLength <= 0) && inputString.trim().length() <= maxLength;
    }

    /**
     * This method validate if the number is negative or not
     *
     * @return (boolean) : it return true if the parameter number is less than 0(zero)
     */
    private static boolean isNumberNegative(int number) {
        return number <= 0;
    }

}