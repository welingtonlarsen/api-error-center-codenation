package com.api.error.center.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response<T> {

    private T data;
    private List<String> errors;

    public T getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void addError(String error) {
        if (errors == null)
            errors = new ArrayList<>();
        errors.add(error);
    }
}