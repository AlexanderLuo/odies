package org.share.odies.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class ExpressionUtil {


    public ExpressionUtil() {
    }

    public static long getScore(StandardEvaluationContext itemContext, Expression expression) {
        if (expression == null) {
            return System.currentTimeMillis();
        } else {
            Object obj = expression.getValue(itemContext);
            if (obj instanceof Date) {
                return ((Date)obj).getTime();
            } else if (obj instanceof LocalDateTime) {
                return ((LocalDateTime)obj).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            } else if (obj instanceof Number) {
                return ((Number)obj).longValue();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static String getKey(Object ro, Expression expression) {
        return expression.getValue(ro).toString();
    }

}
