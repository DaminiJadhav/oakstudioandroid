package com.sdaemon.oakstudiotv.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public interface AppConstants {
    boolean APP_DEBUGGER = true;

    /******LOCAL*******/
    String BASE_URL = "http://192.168.1.31:8080/api/";


    // *********************Sinch Constant***************************************//
    String SINCH_APPLICATION_KEY = "9eee1667-e14f-4214-8a62-";
    String SINCH_APPLICATION_SECRET = "sN/JwqO92UWszEeLOZw==";
    String SINCH_ENVIRONMENT_HOST = "clientapi.sinch.com";
//    String SINCH_ENVIRONMENT_HOST = "sandbox.sinch.com";

    String CALLING_START = "calling_start";
    String CALLING_ACTION = "calling_action";
    String DISMISS_DIALOG = "dismiss_dialog";
    String OPEN_DIALOG = "open_dialog";
    String RECEIVE_CALL = "RECEIVE_CALL";
    String END_CALL = "END_CALL";

    String USERID = "sll";
    String RECKEY = "lls";

//    String USERID = "lls";
//    String RECKEY = "sll";

    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;


    /*** Messages for user interaction*/
    String SUCCESS_TRUE = "true", SUCCESS_FALSE = "false";
    String UNEXPECTED_RESPONSE = "Whoops! Something is happen unexpectedly. Response is not in proper format.";
    String PARSING_ERROR = "Whoops! Something is happen unexpectedly. Exception in data parsing.";
    String EXCEPTION = "Whoops! Something is happen unexpectedly. Exception in data processing.";

    /*** Constant for Intent calling*/
    int GALLERY = 111, CAMERA = 112, CROP = 113, SUCCESS_0 = 0;
    int MY_PROFILE = 4;

    /*** Image Storage Path*/
    String IMAGE_DIRECTORY = "/DCIM/PICTURES";
    String IMAGE_DIRECTORY_CROP = "/DCIM/CROP_PICTURES";
    String IMAGE_DIRECTORY_COMPRESS = "/DCIM/COMPRESS";

    String FONTNAME = "fonts/NotoSerif-Regular.ttf";
    String FONTNAMEBOLT = "fonts/NotoSerif-Bold.ttf";

    int REQUEST_PERMISSION = 101;
    int SELECT_FILE = 11;
    int REQUEST_CAMERA = 100;
    int PIC_CROP = 1;
    int REQUEST_TAKE_GALLERY_VIDEO = 301;
    int LOGIN_FRAGMENT = 1;
    int REGISTRATION_FRAGMENT = 2;
    int EDIT_PROFILE_FRAGMENT = 3;

    /*** SimpleDateFormat*/
    SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat(
            "dd MMM yyyy", Locale.getDefault());
    SimpleDateFormat DD_MM_YY = new SimpleDateFormat(
            "dd/MM/yy", Locale.getDefault());
    SimpleDateFormat YYYY_MM_DD_NEW = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat MMMMM_YYYY = new SimpleDateFormat("MMMM yyyy",
            Locale.getDefault());
    SimpleDateFormat HH_MM_AM_PM = new SimpleDateFormat("hh:mm a",
            Locale.getDefault());
    SimpleDateFormat HH_MM_SS = new SimpleDateFormat("hh:mm:ss",
            Locale.getDefault());
    SimpleDateFormat DD_MMMM_YYYY = new SimpleDateFormat(
            "dd MMMM, yyyy", Locale.getDefault());
    /*** SimpleDateFormat*/
    SimpleDateFormat MMM_DD_YYYY_HH_MM_A = new SimpleDateFormat(
            "dd MMM yyyy hh:mm a", Locale.getDefault());







    /*** Validation ragular expression*/
    Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^([a-zA-Z0-9._-]+)@{1}(([a-zA-Z0-9_-]{1,67})|([a-zA-Z0-9-]+\\.[a-zA-Z0-9-]{1,67}))\\.(([a-zA-Z0-9]{2,6})(\\.[a-zA-Z0-9]{2,6})?)$");

    /*** Notification Status*/
    String ACCEPT_REQUEST = "ACCEPT_REQUEST";
    String ADMIN = "ADMIN";
    String USER_ACTIVE = "USER_ACTIVE";
    String USER_INACTIVE = "USER_INACTIVE";
    String AGENT_ACTIVE = "AGENT_ACTIVE";
    String AGENT_INACTIVE = "AGENT_INACTIVE";

    /*** Variable For Condition Check*/
    String OFF = "OFF";
    String ON = "ON";
    String ACTIVE_STATUS = "1";
    String INACTIVE_STATUS = "0";
    String REGISTRATION = "registration";
    String LOGIN = "login";
    String AFTER_LOGIN = "after_login";
    String DEVICE_TYPE = "android";
    String HEIGHT = "height";
    String WEIGHT = "weight";
    String FROM_NOTIFICATION = "from_notification";
    String FROM_SPLASH_NOTIFICATION = "from_splash_notification";
    String FEMALE = "Female";
    String MALE = "Male";
    String FACEBOOK = "facebook";
    String REFRESH = "REFRESH";
    String ACTION = "ACTION";
    String YEAR = "YEAR";
    String MONTH = "MONTH";
    String NEXT = "NEXT";
    String PREV = "PREV";
    String PROFILE = "teacher";


    //**************************REQUEST PARAM END***************************************
    String PN_PASSWORD = "password";
    String PN_OLD_PASSWORD = "old_password";
    String PN_DEVICE_TYPE = "device_type";
    String PN_TYPE = "type";
    String PN_FORGOT_TOKEN = "forgot_token";
    String PN_CONTENT = "content";
    String PN_NAME = "name";
    String PN_VALUE = "value";
    String PN_TOKEN = "Authorization";
    String PN_ACCEPT = "Accept";
    String PN_VERSION = "version";
    String PN_FACEBOOK_ID = "facebook_id";
    String PN_PLAN_ID = "plan_id";
    String PN_TITLE = "title";
    String PN_COUNTRY_CODE = "country_code";
    String PN_MEALS_ID = "meals_id";
    String PN_MESSAGE = "message";
    String PN_DESCRIPTION = "description";
    String PN_NOTIFICATION_STATUS = "notification_status";
    String PN_ITEM_ID = "item_id";
    String PN_DATE = "date";
    String PN_IMAGE = "image";
    String PN_FIRST_NAME = "first_name";
    String PN_LAST_NAME = "last_name";
    String PN_SEARCH_DATE = "search_date";
    String PN_PHONE_NO = "phone_no";
    String PN_GENDER = "gender";
    String PN_NEW_PASSWORD = "new_password";
    String PN_ID = "id";
    String PN_PAGE = "page";
    String PN_FEES = "fees";
    String PN_PLAN_STATUS = "plan_status";
    String PN_JSON_DATA = "json_data";
    String PN_TRANSACTION_ID = "transaction_id";
    String PN_UPGRATE_STATUS = "upgrate_status";
    String PN_WEIGHT_STATUS = "weight_status";
    String PN_WEIGHT_VALUE = "weight_value";
    String PN_HOW_ACTIVE = "how_active";
    String PN_HEIGHT = "height";
    String PN_WEIGHT = "weight";
    String PN_DOB = "dob";
    String PN_TOPIC = "topic";
    String PN_LEVEL = "level";
    String PN_LEVEL_ID = "level_id";
    String PN_APP_VERSION = "app_version";
    String PN_MOBILE_NO = "mobile_no";
    String PN_DEVICE_ID = "device_key";
    String PN_USER_TYPE = "user_type";
    String PN_NOTIFICATION_kEY = "notification_key";
    String PN_SUBSCRIPTION_ID = "subscription_id";

    String PN_PAYMENT_TYPE = "type";
    String PN_FILTER_TYPE = "filter_type";
    String PN_SEARCH_TYPE = "search_type";
    String PN_SEARCH_NAME = "search_name";
    String PN_MIN_PRICE = "min_price";
    String PN_MAX_PRICE = "max_price";
    String PN_CLASS_ARR = "class_arr";
    String PN_SUBJECT_ARR = "subject_arr";
    String PN_TOPIC_ARR = "topic_arr";
    String PN_MIN_STRENGTH = "min_strength";
    String PN_MAX_STRENGTH = "max_strength";
    String PN_AVAILABLE = "available";
    String PN_GROUP_ID = "group_id";
    String PN_GROUP_NAME = "group_name";
    String PN_SUBSCRIBER_USER_ID = "subcriber_user_id";
    String PN_NOTE = "note";
    String PN_GROUP_IMAGE = "group_image";
    String PN_EMAIL = "email";
    String PN_MONTH = "month_arr";
    String PN_SORT_TYPE = "sort_type";
    String PN_REFFERAL_CODE = "referral_code";
    String PN_DISCUSSION_ID = "discussion_id";
    String PN_USER_ID = "user_id";


    String PN_GENRE = "GenresID";
    String REFRESH_LIST = "refresh_list";
    String PN_LINK = "link";
    String MESSAGE_EN = "message_en";
    String NOTIFICATION = "notification";
    String PN_AMOUNT = "amount";

    String PN_COREPLAN_ID = "CorePlanId";
    String PN_PERIOD_ID = "PeriodId";
    String PN_UNIQUE_ID = "UniqueId";
    String PN_COUPAN_CODE = "CoupanCode";
    String PN_PLAN_COST = "Plan_Cost";
    String PN_PLAN_NAME = "plan_name";





    String MONTHLY = "monthly";
    String QUARTERLY = "quarterly";
    String YEARLY = "yearly";







    //      Parameter name for request on remote server

    String API_KEY = "";
    String ITEM = "item";
    String TYPE = "type";
    String CUSTOMER = "customer";
    String VENDOR = "vendor";
    String INTERMEDIATE = "intermediate";
    String NOTIFICATION_TYPE = "notification_type";
    String RADIOGRPTYPE = "RADIOGRPTYPE";
    String MINPRICE = "MINPRICE";
    String MAXPRICE = "MAXPRICE";
    String MINSTRENGTH = "MINSTRENGTH";
    String MAXSTRENGTH = "MAXSTRENGTH";
    String TOPIC = "TOPIC";
    String CLASSCBOX = "CLASSCBOX";
    String SUBJECTCBOX = "SUBJECTCBOX";
    String FILTERTYPE = "FILTERTYPE";
    String GROUP_ID = "GROUP_ID";
    String USER_ID = "USER_ID";
    String FROM_ALLGROUP = "FROM_ALLGROUP";
    String FROM_MYGROUP = "FROM_MYGROUP";
    String STUDENT = "student";
    String TEACHER = "educator";
    String SORTTYPE = "SORTTYPE";
    String FROMSEARCH = "fromsearch";
    String FROMSEARCHTYPE = "fromsearch";
    String GOOGLE_PAY = "google_pay";
    String PENDING = "pending";
}