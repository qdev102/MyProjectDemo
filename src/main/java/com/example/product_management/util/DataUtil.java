package com.example.product_management.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class DataUtil {

    public static boolean isValidEmail(String email) {
        // ^[\\w\\.-]+: Phần tên người dùng có thể chứa các ký tự chữ cái, số, dấu chấm (.), và dấu gạch ngang (-)
        // @[\\w\\.-]+: Phần domain bắt đầu với ký tự @ và theo sau là các ký tự tương tự như phần tên người dùng
        // \\.[a-zA-Z]{2,}$: Phần đuôi của domain phải là các ký tự chữ cái và có độ dài từ 2 trở lên
        Pattern pattern = Pattern.compile( "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
        // Kiểm tra nếu email là null hoặc rỗng
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Trả về kết quả khớp giữa email và pattern
        return pattern.matcher(email).matches();
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }


    public static boolean isNullOrZero(Double value) {
        return (value == null || value == 0);
    }


    public static boolean isNullOrZero(String value) {
        return (value == null || value.trim().equals(""));
    }


    public static boolean isNullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    public static boolean isNullOrZero(Short value) {
        return value == null;
    }

    public static Date formatDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);  // Định dạng của chuỗi ngày
        try {
            // Chuyển đổi String thành Date
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();  // Xử lý ngoại lệ nếu chuỗi không đúng định dạng
        }
        return null;
    }

}
