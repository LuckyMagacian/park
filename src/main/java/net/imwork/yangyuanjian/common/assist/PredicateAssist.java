package net.imwork.yangyuanjian.common.assist;

import java.util.function.Predicate;

public interface PredicateAssist {
    Predicate<Object> isNull=e->e==null;
    Predicate<Object> notNull=isNull.negate();

    Predicate<String> isNullOrEmpty=e->isNull.test(e)||e.isEmpty();
    Predicate<String> notNullOrEmpty=isNullOrEmpty.negate();

    Predicate<String> isInteger=e->e.matches("[0-9]{1,100}");
    Predicate<String> isDeciaml=e->e.matches("[0-9]+\\.[0-9]{1,20}");
}
