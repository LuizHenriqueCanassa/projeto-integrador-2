package br.com.luizcanassa.projetintegrador2.utils;

import lombok.experimental.UtilityClass;
import org.thymeleaf.expression.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

@UtilityClass
public final class DateUtils {

    public String getShortNameDayOfWeek(final LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"))
                .toUpperCase()
                .replace(".", "");
    }

    public String getShortNameOfMonth(final LocalDateTime localDateTime) {
        return localDateTime.getMonth()
                .getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"))
                .toUpperCase()
                .replace(".", "");
    }

    public List<String> getLastShortNameDayOfWeek(final Integer quantityDaysBack) {
        List<String> namesDayOfWeek = new ArrayList<>();

        for (int i = quantityDaysBack; i >= 1; i--) {
            namesDayOfWeek.add(getShortNameDayOfWeek(LocalDateTime.now().minusDays(-i)));
        }

        Collections.reverse(namesDayOfWeek);

        return namesDayOfWeek;
    }

    public List<String> getShortNameOfMonths(final Integer quantityMonthsBack) {
        List<String> namesOfMonth = new ArrayList<>();

        for (int i = quantityMonthsBack; i >= 1; i--) {
            namesOfMonth.add(getShortNameOfMonth(LocalDateTime.now().minusMonths(i-1)));
        }
//
//        Collections.reverse(namesOfMonth);

        return namesOfMonth;
    }

    public LocalDate dateStartOfMonth() {
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate dateEndOfMonth() {
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndOfDay(calendar);
        return calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
