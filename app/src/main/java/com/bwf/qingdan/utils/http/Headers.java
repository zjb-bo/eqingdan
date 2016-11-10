package com.bwf.qingdan.utils.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016/10/17.
 * Tips:
 */

public class Headers {
    private List<String> namesAndValues;
    private Headers(Builder builder){
        this.namesAndValues = builder.namesAndValues;
    }
    public List<String> getNamesAndValues() {
        return namesAndValues;
    }
    public static class Builder{
        private List<String> namesAndValues;
        public Builder(){
            namesAndValues = new ArrayList<>();
        }
        public Builder addHeader(String name,String value){
            namesAndValues.add(name);
            namesAndValues.add(value);
            return this;
        }
        public Builder addHeaders(Headers headers){
            namesAndValues.addAll(headers.namesAndValues);
            return this;
        }
        public Headers build(){
            return new Headers(this);
        }
    }
}
