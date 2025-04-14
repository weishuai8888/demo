package com.weishuai.rateLimiter.entity.po;

import com.google.common.collect.Lists;
import lombok.Setter;

import java.util.ArrayList;


/**
 *
 * @author ws001
 */
@Setter
public class Demo {

    private Byte[] arr;

    public static final Byte[] b = new Byte[1024 * 1024];

    public static void main(String[] args) {
        ArrayList<Byte[]> list = Lists.newArrayList();
        while (true) {
            list.add(b);
        }
    }

}
