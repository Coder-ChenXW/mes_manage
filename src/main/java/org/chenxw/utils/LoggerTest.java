package org.chenxw.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;



@Slf4j
public class LoggerTest {

    private static final Logger logger = CuzLoggerFactory.getLogger(LoggerTest.class);


    public static void main(String[] args) {


        logger.info("hello");
        logger.warn("hello");
        logger.error("hello");

        logger.info("hello {},{} fucker", "丢", 1);

//        String str1 = "[a] - [b] param: abc";
//        String str2 = "[a] param: abc";
//        String regex = "(\\[.*?])+?( - \\[.*?])?(.*)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str2);
//        while (matcher.find()){
//            String group1 = matcher.group(1);
//            String group2 = matcher.group(2);
//            String group3 = matcher.group(3);
//            System.out.println(group1);
//            System.out.println(group2);
//            System.out.println(group3);
//        }

    }
}
