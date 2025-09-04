package com.hekoinc.merchant_service.utils;

import java.util.function.Consumer;

public class AppUtil {
    public static <T>  void updateField(Consumer<T> setter, T newValue) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}