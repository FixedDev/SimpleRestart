/*******************************************************************************
 *  Copyright (C) SparkNetwork - All Rights Reserved
 *   * Unauthorized copying of this file, via any medium is strictly prohibited
 *   * Proprietary and confidential
 *   * Written by Gilberto Garcia <gilbertodamian14@gmail.com>, May 2018
 *
 ******************************************************************************/

package net.minebukket.restart.util;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static String getHumanReadableDate(final long time) {
        long remainingTime = time / 1000;

        StringJoiner joiner = new StringJoiner(" ");

        long years;
        long months;
        long weeks;
        long days;
        long hours;
        long minutes;
        long seconds;

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 365) {
            years = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 365);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 365;
            joiner.add(years + "");
            joiner.add(years > 1 ? "años" : "año");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 30) {
            months = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 30);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 30;
            joiner.add(months + "");
            joiner.add(months > 1 ? "meses" : "mes");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 7) {
            weeks = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 7);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 7;
            joiner.add(weeks + "");
            joiner.add(weeks > 1 ? "semanas" : "semana");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1)) {
            days = remainingTime / TimeUnit.DAYS.toSeconds(1);
            remainingTime %= TimeUnit.DAYS.toSeconds(1);
            joiner.add(days + "");
            joiner.add(days > 1 ? "dias" : "dia");
        }

        if (remainingTime >= TimeUnit.HOURS.toSeconds(1)) {
            hours = remainingTime / TimeUnit.HOURS.toSeconds(1);
            remainingTime %= TimeUnit.HOURS.toSeconds(1);
            joiner.add(hours + "");
            joiner.add(hours > 1 ? "horas" : "hora");
        }

        if (remainingTime >= TimeUnit.MINUTES.toSeconds(1)) {
            minutes = remainingTime / TimeUnit.MINUTES.toSeconds(1);
            remainingTime %= TimeUnit.MINUTES.toSeconds(1);
            joiner.add(minutes + "");
            joiner.add(minutes > 1 ? "minutos" : "minuto");
        }

        if (remainingTime >= 0) {
            seconds = remainingTime;
            joiner.add(seconds + "");
            joiner.add(seconds > 1 ? "segundos" : seconds == 0 ? "segundos" :  "segundo");
        }
        return joiner.toString();
    }

    public static String getRelativeHumanTime(final long time){
        long remainingTime = time / 1000;

        StringBuilder stringBuilder = new StringBuilder();

        long years;
        long months;
        long weeks;
        long days;
        long hours;
        long minutes;
        long seconds;

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 365) {
            years = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 365);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 365;
            stringBuilder.append(years + "y");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 30) {
            months = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 30);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 30;
            stringBuilder.append(months + "M");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1) * 7) {
            weeks = remainingTime / (TimeUnit.DAYS.toSeconds(1) * 7);
            remainingTime %= TimeUnit.DAYS.toSeconds(1) * 7;
            stringBuilder.append(weeks + "w");
        }

        if (remainingTime >= TimeUnit.DAYS.toSeconds(1)) {
            days = remainingTime / TimeUnit.DAYS.toSeconds(1);
            remainingTime %= TimeUnit.DAYS.toSeconds(1);
            stringBuilder.append(days + "d");
        }

        if (remainingTime >= TimeUnit.HOURS.toSeconds(1)) {
            hours = remainingTime / TimeUnit.HOURS.toSeconds(1);
            remainingTime %= TimeUnit.HOURS.toSeconds(1);
            stringBuilder.append(hours + "h");
        }

        if (remainingTime >= TimeUnit.MINUTES.toSeconds(1)) {
            minutes = remainingTime / TimeUnit.MINUTES.toSeconds(1);
            remainingTime %= TimeUnit.MINUTES.toSeconds(1);
            stringBuilder.append(minutes + "m");
        }

        if (remainingTime >= 0) {
            seconds = remainingTime;
            stringBuilder.append(seconds + "s");
        }
        return stringBuilder.toString();
    }

    public static long parseRelativeHumanTime(final String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convert(final int value, final char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }
            default: {
                return -1L;
            }
        }
    }
}
