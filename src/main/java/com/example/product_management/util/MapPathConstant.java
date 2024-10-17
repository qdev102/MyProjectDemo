package com.example.product_management.util;

import java.util.Map;

public class MapPathConstant {

    public static final Map<String, String> PERSONNEL_PROPERTY_PATH = Map.of(
            "code", "Mã nhân sự",
            "name", "Tên",
            "birthDate", "Ngày sinh"
    );

    public static final Map<String, String> DEPARTMENT_PROPERTY_PATH = Map.of(
            "code", "Mã phòng ban",
            "name", "Tên phòng ban");
}
