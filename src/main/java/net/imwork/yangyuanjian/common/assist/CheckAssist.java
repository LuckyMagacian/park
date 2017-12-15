package net.imwork.yangyuanjian.common.assist;

import java.util.function.Predicate;

public interface CheckAssist {
    Predicate<Object> isNull=e->e==null;

    Predicate<String> isEmpty=e->e.isEmpty();

    Predicate<String> isNullOrEmpty=e->isNull.test(e)||isEmpty.test(e);

    Predicate<String> isInteger=e->e==null?false:e.matches("[0-9]+");

    Predicate<String> isDecimal=e->e==null?false:e.matches("[0-9]+[0-9\\.]*]");

    Predicate<String> isNameOrAddress=e->e==null?false:e.matches("[\\u4e00-\\u9fa5a-zA-Z\\(\\)\\-\\[\\] ]+[\\u4e00-\\u9fa50-9a-zA-Z\\(\\)\\-\\[\\] ]*");

    Predicate<String> isYYYYMMddHHmmss=e->e==null?false:e.matches("(20[0-2][0-9][0-1][0-9][0-3][0-9])([0-2][0-9]([0-5][0-9]){2}){0,1}");



}
