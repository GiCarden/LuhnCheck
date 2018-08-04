import java.util.Scanner;

/**
 * Financial: credit card number validation.
 *
 * This class applies, Hans Luhn of IBM who proposed an algorithm for validating credit card
 * numbers. The algorithm is useful for determining whether a card number was entered
 * correctly or whether a credit card was scanned correctly by a scanner.
 *
 * Credit card numbers are generated following this validity check, commonly known as the
 * Luhn check or the Mod 10 check, which can be described as follows:
 *
 *  1. Starting from the rightmost digit double the value of every second digit.
 *
 *  2. If doubling of a number results in a two digits number i.e greater than 9,
 *  then add the digits of the product, to get a single digit number.
 *
 *  3. Now take the sum of all the digits.
 *
 *  4. If the total modulo 10 is equal to 0 (if the total ends in zero)
 *  then the number is valid according to the Luhn formula; else it is not valid.
 */
class LuhnCheck {

//Variable(s)-
    private static final int CREDIT_CARD_NUMBER_MIN_LENGTH = 13;
    private static final int CREDIT_CARD_NUMBER_MAX_LENGTH = 16;

    private static int sumOfOddPlacedDigits = 0;
    private static int sumOfEvenPlacedDigits = 0;

//Private Method(s)-
    /**
     * Repeated-Division digit counter, to validate user input.
     *
     * @param value to test.
     * @param min lowest acceptable value.
     * @param max highest acceptable value.
     * @return true if value is between min and max, else false.
     */
    private static boolean lengthValidator(Long value, int min, int max) {

        int creditCardLength = 0;

        while(value > 0) {

            creditCardLength++;
            value  = value / 10;
        }
        return ((creditCardLength >= min) && (creditCardLength <= max));
    }

    /**
     * Add all digits in odd places from right to left from card number.
     *
     * @param digit to add.
     */
    private static void oddPlacedDigitsSum(int digit) { sumOfOddPlacedDigits += digit; }

    /**
     * Add and double every second digit from right to left. If doubling of a digit results in a two-digit number,
     * add up the two digits to get a single-digit number.
     *
     * @param digit to add.
     */
    private static void evenPlacedDigitsSum(int digit) {

        sumOfEvenPlacedDigits += (digit * 2  > 9) ? sumOfTwoDigits_v2(digit * 2) : digit * 2;
    }

    /**
     * Add up each individual digit of a number, resulting in a single-digit integer.
     *
     * @param number to split into digits.
     * @return single-digit number.
     */
    private static int sumOfTwoDigits(int number) {

        int temp = 0;

        while(number > 0) {

            temp += number % 10;
            number /= 10;
        }
        return temp;
    }

    /**
     * Add up each individual digit of a number, resulting in a single-digit integer, version 2.
     *
     * @param number to split into digits.
     * @return single-digit number.
     */
    private static int sumOfTwoDigits_v2(int number) {

        int temp = 0;

        temp += number % 10;
        number /= 10;
        temp += number;

        return temp;
    }

    /**
     * Determines if credit card number is valid.
     *
     * @param creditCardNumber to test.
     * @return true if the card number is valid, else false.
     */
    private static boolean isCreditCardNumberValid(long creditCardNumber) {


        while(creditCardNumber > 0) {

            oddPlacedDigitsSum((int) (creditCardNumber % 10));
            creditCardNumber /= 10;

            evenPlacedDigitsSum((int) (creditCardNumber % 10));
            creditCardNumber /= 10;
        }
        return ((sumOfOddPlacedDigits + sumOfEvenPlacedDigits) % 10 == 0);
    }

    /**
     * Prints a message to the user.
     *
     * @param creditCardNumber long
     * @param isCardNumberValid boolean
     */
    private static void printInfo(long creditCardNumber, boolean isCardNumberValid) {

        System.out.printf("The Credit Card Number %s is %s",
                creditCardNumber, isCardNumberValid ? "Valid" : "invalid, Try Again: ");
    }

    /**
     * Reset Class variables back to init state.
     */
    private static void resetVariables() {

        sumOfEvenPlacedDigits = 0;
        sumOfOddPlacedDigits = 0;
    }

//Package Private Method(s)-
    /**
     * User input validator,
     */
    static void inputProcessor() {

        Scanner cin = new Scanner(System.in);
        boolean isValid = false;
        long creditCardNumber;

        System.out.print("\nEnter a Credit Card Number to Validate: ");

        do {
            try {

                creditCardNumber = Long.parseLong(cin.nextLine().replaceAll("\\s","").trim());

                if (lengthValidator(creditCardNumber, CREDIT_CARD_NUMBER_MIN_LENGTH, CREDIT_CARD_NUMBER_MAX_LENGTH)) {

                    isValid = isCreditCardNumberValid(creditCardNumber);
                    printInfo(creditCardNumber, isValid);
                    resetVariables();
                } else {

                    System.out.print("Invalid Credit Card Number Length, Try Again: ");
                }

            } catch (NumberFormatException | NullPointerException e) {

                System.out.print("Invalid Credit Card Number Format, Try Again: ");
            }
        } while (!isValid);
    }
    
}//End of Class.
