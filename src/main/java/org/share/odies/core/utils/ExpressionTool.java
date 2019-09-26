package org.share.odies.core.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


public class ExpressionTool {


    public static String strValue(Object rootObject, String expression){
        Object res = value(rootObject,expression);
        return (String)res;
    }


    public static Long longValue(Object rootObject, String expression){
        Object res = value(rootObject,expression);
        return (Long)res;
    }


    private static Object value(Object rootObject, String expression){
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(rootObject);
        Expression spel = parser.parseExpression(expression);

        return spel.getValue(standardEvaluationContext);

    }

}
