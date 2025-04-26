package com.pizzaparty.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for managing order codes.
 * Provides methods to generate and increment the order counter.
 */
public class Utils {

    // Fixed prefix for the order code
    public static final String PREFIX = "COD";

    // Static constant for the date format
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");

    /**
     * Increments the counter as a string (handles 4-digit format).
     *
     * @param counter Current counter as a string.
     * @return The new incremented counter.
     */
    public static String increaseCounter(String counter) {
        // Add one to the counter represented as a string
        long counterLong = Long.parseLong(counter);  // Convert the string to long
        counterLong++;  // Increment the counter
        return String.format("%04d", counterLong);  // Format back to a 4-digit string
    }

    /**
     * Generates the order code based on the current date and a progressive counter.
     *
     * @param lastDate The date of the last generated order.
     * @param counter The order counter.
     * @return The new order code.
     */
    public static String generateOrderCode(String lastDate, String counter) {
        // Get the current date in ddMMyyyy format
        String currentDate = LocalDate.now().format(DATE_FORMAT);

        // If the current date is different from the last date, reset the counter
        if (!currentDate.equals(lastDate)) {
            counter = "0001";  // Reset the counter
        }

        return PREFIX + "-" + currentDate + "-" + counter;
    }
}
