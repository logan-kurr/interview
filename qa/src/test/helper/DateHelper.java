package test.helper;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;


public class DateHelper {

    public static final String CONFIRMATION_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Parses provided Date and returns Year
     * @param date: Date to be parsed
     * @return
     */
    public static String parseYear(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONFIRMATION_DATE_FORMAT);
        LocalDate specificDate = LocalDate.parse(date, formatter);
        return String.valueOf(specificDate.getYear());
    }

    /**
     * Parses provided Date and returns specific Month
     * @param date: Date to be parsed
     * @return
     */
    public static Month parseMonth(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONFIRMATION_DATE_FORMAT);
        LocalDate specificDate = LocalDate.parse(date, formatter);
        return Month.of(specificDate.getMonthValue());
    }

    /**
     * Parses provided Date and returns day of the Month
     * @param date: Date to be parsed
     * @return
     */
    public static int parseDay(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONFIRMATION_DATE_FORMAT);
        LocalDate specificDate = LocalDate.parse(date, formatter);
        return specificDate.getDayOfMonth();
    }

    /**
     * Retrieves the Current Date with the Default Format
     * @return
     */
    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONFIRMATION_DATE_FORMAT);
        return LocalDate.now().format(formatter);
    }

    /**
     * Using the provided Date formats and retrieves new dats with
     * @param date: Date to be parsed
     * @param daysToAdd: Amount of days to add to Date
     * @return
     */
    public static String getFutureDate(String date, int daysToAdd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONFIRMATION_DATE_FORMAT);
        LocalDate specificDate = LocalDate.parse(date, formatter);
        LocalDate newDate = specificDate.plusDays(daysToAdd);
        return newDate.format(formatter);
    }
}
