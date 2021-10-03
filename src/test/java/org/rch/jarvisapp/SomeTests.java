package org.rch.jarvisapp;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class SomeTests {

    @Test
    void start(){
        System.out.println("123");
    }

    @Test
    void JSON2String(){
        String str1 = "{\"qwe\" : \"asd\"}";
        JSONObject obj = new JSONObject(str1);
        System.out.println(obj.toString());
    }

    @Test
    void NullTest(){
        Integer i;

    }

}
