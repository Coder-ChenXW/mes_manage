package org.chenxw.authentication.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String SECRET = "CodeSheepSecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final Map<String, List<String>> roleButtons;


    static {
        roleButtons = new HashMap<>();

        List<String> normalButtons = new ArrayList<>();


        List<String> cutter = new ArrayList<>();
        cutter.add("employee.content.detail");
        cutter.add("order.header.cutting");
        cutter.add("order.content.detail");
        cutter.add("employee.content.detail");


        List<String> adminButtons = new ArrayList<>();
        adminButtons.add("employee.header.add");
        adminButtons.add("employee.content.detail");
        adminButtons.add("employee.content.update");
        adminButtons.add("employee.content.delete");

        adminButtons.add("product.header.add");
        adminButtons.add("product.content.detail");
        adminButtons.add("product.content.update");
        adminButtons.add("product.content.delete");


        adminButtons.add("order.header.add");
        adminButtons.add("order.header.cutting");
        adminButtons.add("order.header.schedule");
        adminButtons.add("order.header.finish");

        adminButtons.add("order.content.detail");
        adminButtons.add("order.content.update");
        adminButtons.add("order.content.delete");

        List<String> hrButtons = new ArrayList<>();

        hrButtons.add("employee.header.add");
        hrButtons.add("employee.content.detail");
        hrButtons.add("employee.content.update");
        hrButtons.add("employee.content.delete");

        hrButtons.add("product.header.add");
        hrButtons.add("product.content.detail");
        hrButtons.add("product.content.update");

        hrButtons.add("order.header.add");
        hrButtons.add("order.header.cutting");
        hrButtons.add("order.header.schedule");

        hrButtons.add("order.content.detail");
        hrButtons.add("order.content.update");

        roleButtons.put("admin", adminButtons);
        roleButtons.put("hr", hrButtons);
        roleButtons.put("cutter", cutter);
        roleButtons.put("normal", normalButtons);
    }
}