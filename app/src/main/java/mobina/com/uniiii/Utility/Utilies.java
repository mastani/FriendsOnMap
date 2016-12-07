package mobina.com.uniiii.Utility;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.HashMap;

import mobina.com.uniiii.abstracts.User;

public class Utilies {

    public final static String URL = "http://mastani.ir/mobina/";

    public static User me;

    public static HashMap<String, String> localUsers = new HashMap<String, String>();
    public static ArrayList<User> syncedUsers = new ArrayList<User>();
    public static ArrayList<User> friendsUsers = new ArrayList<User>();
    public static ArrayList<User> requestsUsers = new ArrayList<User>();

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isMobileValid(CharSequence mobile) {
        if (mobile.length() != 11) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(mobile).matches();
        }
    }

    public static String convertPhone(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber iranNumberProto = phoneUtil.parse(number.trim(), "IR");
            return phoneUtil.format(iranNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            return number;
        }
    }
}
