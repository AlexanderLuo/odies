package org.share.odies.trans.impl;


import org.share.odies.trans.ConvertorMatcher;
import org.share.odies.trans.ConvertorRegistry;
import org.share.odies.trans.ValueConvertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultConvertorRegistry implements ConvertorRegistry {

    private Map<Class, ValueConvertor> level1Container = new HashMap<>();
    private List<Bucket> level2Container = new ArrayList<>();


    @Override
    public <V> void registerConvertor(ConvertorMatcher matcher, ValueConvertor<V> convertor) {
        level2Container.add(new Bucket(matcher, convertor));
    }

    @Override
    public <V> void registerConvertor(Class<V> clazz, ValueConvertor<V> convertor) {
        level1Container.put(clazz, convertor);
    }

    @Override
    public ValueConvertor<Object> findConvertor(Class clazz) {
        ValueConvertor vc = level1Container.get(clazz);
        if (vc == null) {
            for (Bucket b : level2Container) {
                if (b.convertorMatcher.isMatch(clazz)) {
                    vc = b.valueConvertor;
                    break;
                }
            }
        }
        return vc;
    }

    @Override
    public boolean isSupportedPropertyType(Class clazz) {
        return findConvertor(clazz) != null;
    }

    private static class Bucket {
        ValueConvertor valueConvertor;
        ConvertorMatcher convertorMatcher;

        public Bucket(ConvertorMatcher convertorMatcher, ValueConvertor valueConvertor) {
            this.valueConvertor = valueConvertor;
            this.convertorMatcher = convertorMatcher;
        }
    }
}
