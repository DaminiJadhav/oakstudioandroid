package com.sdaemon.oakstudiotv.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtilsApp {
    public static Date getDateFromMS(String dateTimeMS) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (!TextUtils.isEmpty(dateTimeMS)) {
                long timestamp = getLong(dateTimeMS);
                calendar.setTimeInMillis(timestamp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static String getFormattedDateTimeString(SimpleDateFormat sdf, Date date) {
        try {
            if (date == null) return "";
            String string = sdf.format(date);
            return string;
        } catch (Exception e) {
            return date.toString();
        }
    }

    public static String getUTCFormattedDateTimeString(SimpleDateFormat sdf, Date date) {
        try {
            if (date == null) return "";
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }


    public static String getFormattedDateTimeString(SimpleDateFormat sdf, String dateTimeMS) {
        try {
            Date date = getDateFromMS(dateTimeMS);
            if (date == null) return "";
            String string = sdf.format(date);
            return string;
        } catch (Exception e) {
            return dateTimeMS;
        }
    }

    static public long getLong(String sValue) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        Number parse = null;
        try {
            if (TextUtils.isEmpty(sValue) || sValue.equalsIgnoreCase("null"))
                return 0;
            parse = numberFormat.parse(sValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse == null ? 0 : parse.longValue();
    }

    public static Date parseDate(String dateString) {
        Date date = new Date();
        try {
            if (TextUtils.isEmpty(dateString) || dateString.equalsIgnoreCase("null")) return null;
            Object object = new JSONTokener(dateString).nextValue();
            if (object instanceof JSONArray) return null;
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(dateString);
                if (jsonObject.has("sec"))
                    date = DateUtilsApp.getDateFromSecond(jsonObject.getString("sec"));
            } else {
                if (!TextUtils.isEmpty(dateString) && Utilities.isNumeric(dateString))
                    date = DateUtilsApp.getDateFromSecond(dateString);
                else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (dateString.contains("T")) {
                        if (dateString.contains(".")) {
                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        } else
                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    }
                    date = DateUtilsApp.getDateFromString(simpleDateFormat, dateString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }



    public static Date getDateFromSecond(String dateTimeSecond) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (!TextUtils.isEmpty(dateTimeSecond)) {
                long timestamp = Long.parseLong(dateTimeSecond) * 1000;
                calendar.setTimeInMillis(timestamp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static Date getDateFromString(SimpleDateFormat sdf, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static String convertToLocalTime(SimpleDateFormat input, SimpleDateFormat output, String dateTime) {
        if (TextUtils.isEmpty(dateTime))
            return "N/A";
        if (dateTime.equals("0000-00-00 00:00:00")) {
            return "N/A";
        }
        try {
            if (dateTime.equalsIgnoreCase("N/A")) return "N/A";
            Date updateDate = input.parse(dateTime);
            return output.format(updateDate);
        } catch (Exception e1) {
            e1.printStackTrace();
            return "N/A";
        }
    }


    public static String convertToLocalTimeNew(SimpleDateFormat output, Date updateDate) {
        try {
            return output.format(updateDate);
        } catch (Exception e1) {
            e1.printStackTrace();
            return "N/A";
        }
    }

    public static long getCurrentDateTimeMS() {
        try {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getUTCCurrentDateTimeMS() {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String getTimeLog(String dateTimeMS) {
        SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat("dd MMM yyyy");
        try {
            if (TextUtils.isEmpty(dateTimeMS)) return "N/A";
            if (dateTimeMS.equalsIgnoreCase("N/A")) return "N/A";
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(dateTimeMS));
            if (android.text.format.DateUtils.isToday(cal.getTimeInMillis())) {
                return "Today";
            } else if (isYesterday(cal.getTimeInMillis())) {
                return "Yesterday";
            } else {
                return DD_MMM_YYYY.format(cal.getTime());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return "N/A";
        }
    }

    public static boolean isYesterday(long dateTimeMS) {
        Calendar msgTime = Calendar.getInstance();
        msgTime.setTimeInMillis(dateTimeMS);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DATE) - msgTime.get(Calendar.DATE) == 1) return true;
        return false;
    }

    public static boolean isTomorrow(long dateTimeMS) {
        Calendar msgTime = Calendar.getInstance();
        msgTime.setTimeInMillis(dateTimeMS);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DATE) - msgTime.get(Calendar.DATE) == -1) return true;
        return false;
    }

//
//    public static String formatDateTime(String dateString) {
//        Date date;
//        String formattedDate = "";
//        try {
//          //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//            date = df.parse(dateString);
//         //   formattedDate = new SimpleDateFormat("MMM dd, yyyy hh:mm a ", Locale.getDefault()).format(date);
//            formattedDate = new SimpleDateFormat("hh:mm a ", Locale.getDefault()).format(date);
//        } catch (ParseException e) {// TODO Auto-generated catch blocke.printStackTrace();
//        }
//        return formattedDate;
//    }
//


    public static String getUpdateTime(String dateTimeMS) {
        try {
            SimpleDateFormat HH_MM = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            if (TextUtils.isEmpty(dateTimeMS)) return "N/A";
            if (dateTimeMS.equalsIgnoreCase("N/A")) return "N/A";
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(Long.parseLong(dateTimeMS));
            String string = HH_MM.format(cal.getTime());
            return string;
        } catch (Exception e1) {
            e1.printStackTrace();
            return "N/A";
        }
    }

    public static Date convertUTCToLocalTimeDate(SimpleDateFormat input, SimpleDateFormat output, String dateTime) {
        if (TextUtils.isEmpty(dateTime))
            return new Date();
        if (dateTime.equals("0000-00-00 00:00:00")) {
            return new Date();
        }
        try {
            input.setTimeZone(TimeZone.getTimeZone("UTC"));
            if (dateTime.equalsIgnoreCase("N/A")) return new Date();
            Date updateDate = input.parse(dateTime);
            return updateDate;
        } catch (Exception e1) {
            e1.printStackTrace();
            return new Date();
        }
    }

    public static String getUTCFormattedDateTimeString(SimpleDateFormat sdf, String OurDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(OurDate);

            //this format changeable
            sdf.setTimeZone(TimeZone.getDefault());
            OurDate = sdf.format(value);

        } catch (Exception e) {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    public static String getUTCFormattedTimeString(SimpleDateFormat sdf, String OurDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(OurDate);

            //this format changeable
            //   sdf.setTimeZone(TimeZone.getDefault());
            OurDate = sdf.format(value);
        } catch (Exception e) {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    // Only   HH:mm:ss
    public static String getformattedTimeString(SimpleDateFormat sdf, String OurDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getDefault());
            Date value = formatter.parse(OurDate);

            //this format changeable
            // sdf.setTimeZone(TimeZone.getDefault());
            OurDate = sdf.format(value);

        } catch (Exception e) {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }


// Oak Sttudio

    //  28/03/2019 03:50:42 PM   // localDate
    public String getUTCFormattedTimeString(String localDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SS'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(localDate);

            //this format changeable
            //   sdf.setTimeZone(TimeZone.getDefault());
            localDate = sdf.format(value);
        } catch (Exception e) {
            localDate = "00-00-0000 00:00";
        }
        return localDate;
    }




    public String convertUTCtoLocalDateTime(String utcDate) {

        //  SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            //  d = input.parse("2018-02-02T06:54:57.744Z");
            //  d = input.parse("2019-03-28T07:14:40Z");
            date = input.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(date);
        //    Log.i(String.valueOf(context), "######################  UTC to LOCAl: " + formatted);
        return formatted;

    }


//    String  s = getUTCFormattedTimeString("28/03/2019 03:50:42 PM"); // Local to UTC
//         Log.i(String.valueOf(context), "###################### Local to UTC: " + s);

//    String  s1=   convertUTCtoLocalDateTime("2019-03-28T07:14:40Z");  //UTC to local
//        Log.i(String.valueOf(context), "###################### UTC to LOCAL: " + s1);

//    final Date currentDate = new Date();
//    final SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z"); // Wed, Apr 17, 2019 05:31:27 AM UTC
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        System.out.println("UTC: " + sdf.format(currentDate));
//
//        Log.i(String.valueOf(context), "######################  CURRENT UTC: " + sdf.format(currentDate));
//




//
//
//    //    https://beginnersbook.com/2017/10/java-display-time-in-12-hour-format-with-ampm/
//
//
//    //Displaying current time in 12 hour format with AM/PM
//    java.text.DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
//    String dateString = dateFormat.format(new Date()).toString();
//                System.out.println("Current time in AM/PM: "+dateString);
//
//                Log.i(String.valueOf(mContext), "###################### dateString: " + dateString); // 10.30 AM
//
//    //Displaying current date and time in 12 hour format with AM/PM
//    java.text.DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
//    String dateString2 = dateFormat2.format(new Date()).toString();
//                System.out.println("Current date and time in AM/PM: "+dateString2);
//                Log.i(String.valueOf(mContext), "###################### dateString2: " + dateString2);  // 19/04/2019 10.30 AM
//
//




}
