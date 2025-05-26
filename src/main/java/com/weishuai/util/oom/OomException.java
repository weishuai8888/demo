package com.weishuai.util.oom;

import java.util.HashSet;
import java.util.Set;

public class OomException {
    static class OomObject{

    }

    public static void main(String[] args) {
        System.out.println(OomException.lengthOfLongestSubstringTwoDistinct("ab"));
    }


    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        //在此处写入代码
        int length = 0;
        if(s == null || s.length() == 0){
            return length;
        }
        for(int i = 0; i < s.length();i++){
            int j = i;
            int tmp = 0;
            Set<Character> set = new HashSet<>();
            while(set.size() <= 2 && j < s.length()){
                set.add(s.charAt(j++));
                tmp++;
                if(set.size() == 2){
                    length = length > tmp ? length : tmp;
                }
                if(set.size() > 2){
                    break;
                }
            }
        }
        return length;

    }

}
