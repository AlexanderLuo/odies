package org.share.odies.vo;

import org.share.odies.annotation.Ro;
import org.share.odies.annotation.SortedSet;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoEntityMeta {


    private Class<?> entityClass;
    private Field id;
    private String prefix;
    private int expire;
    private Map<String,SortedSetVO> sortedSetKeys = new HashMap<>();



    public static  RoEntityMeta fromClass(Class entityClass){
        RoEntityMeta roEntityMeta =  new RoEntityMeta();

        Ro ro = (Ro) entityClass.getAnnotation(Ro.class);

        if (ro == null)
            throw new RuntimeException("not find Ro annotation");

        List<Field> filedList = Stream.of(entityClass.getDeclaredFields())
                .filter(x -> x.getAnnotation(Id.class)!=null)
                .peek(x -> x.setAccessible(true))
                .collect(Collectors.toList());


        if (filedList.size() != 1)
            throw new RuntimeException("Ro filed  must have one @ID annotation");

        roEntityMeta.prefix = ro.prefix();
        roEntityMeta.expire = ro.expireSeconds();
        roEntityMeta.entityClass = entityClass;
        roEntityMeta.setId(filedList.get(0));

         Stream.of(entityClass.getDeclaredFields())
                 .filter(x -> x.getAnnotation(SortedSet.class)!=null)
                 .forEach(x ->{
                     x.setAccessible(true);
                     SortedSet fieldSortedSet = x.getAnnotation(SortedSet.class);
                     roEntityMeta.sortedSetKeys.put(x.getName(),new SortedSetVO(fieldSortedSet.key(),fieldSortedSet.score()));
                 });

         roEntityMeta.sortedSetKeys.putIfAbsent(roEntityMeta.getId().getName(), new SortedSetVO("list",""));





        return roEntityMeta;
    }


    public Field getId() {
        return id;
    }


    public void setId(Field id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Map<String, SortedSetVO> getSortedSetKeys() {
        return sortedSetKeys;
    }

    public void setSortedSetKeys(Map<String, SortedSetVO> sortedSetKeys) {
        this.sortedSetKeys = sortedSetKeys;
    }

}
