package com.example.bankapp

import java.util.regex.Matcher
import java.util.regex.Pattern


object PanRepository {
    fun panValidation(panCardNo: String): Boolean? {
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
        val p: Pattern = Pattern.compile(regex)

        if (panCardNo == null) {
            return false;
        }
        val m: Matcher = p.matcher(panCardNo)
        return m.matches()
    }

    fun dobValidation(dobDate: String):Boolean? {
        var result: Boolean? = false
        //day-month-year
        val regex = "^((0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[012])((?:17|18|19|20)[0-9][0-9]))"
        val p: Pattern = Pattern.compile(regex)
        if (dobDate == null) {
            return false;
        }
        val m: Matcher = p.matcher(dobDate)
        //matcher = m  | for me
        if(m.matches())
        {
            result = true

            var year = Integer.parseInt(m.group(3));
            var month = m.group(2)
            var day = m.group(1)
            if ((month.equals("4") || month.equals("6") || month.equals("9") ||
                        month.equals("04") || month.equals("06") || month.equals("09") ||
                        month.equals("11")) && day.equals("31")) {
                result = false;
            } else if (month.equals("2") || month.equals("02")) {
                if (day.equals("30") || day.equals("31")) {
                    result = false;
                } else if (day.equals("29")) {  // leap year? feb 29 days.
                    if (!isLeapYear(year)) {
                        result = false;
                    }
                }
            }
        }
        return result
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
}

//