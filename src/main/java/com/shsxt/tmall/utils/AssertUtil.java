package com.shsxt.tmall.utils;

import com.shsxt.tmall.exception.ParamsException;

public class AssertUtil {


    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }

}
