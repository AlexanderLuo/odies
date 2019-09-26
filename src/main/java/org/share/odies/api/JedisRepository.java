package org.share.odies.api;

import org.share.odies.vo.PageOf;
import org.share.odies.vo.PageResult;
import org.share.odies.vo.SortBy;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface JedisRepository<T> {

//    ok
    <S extends T> T save(T ro);
//    ok
    T findById(Serializable id);
//    ok
    boolean exists(Serializable id);
//    ok
    void delete(Serializable id);
//    ok
    long count();


//  ok
    List<T> findAll();
    PageResult<T> findAll(PageOf pageOf);
    PageResult<T> findAll(PageOf pageOf, SortBy sortBy);


    void deleteAll();


    <S extends T> List<S> saveAll(Iterable<S> ros);
    List<T> findAllById(Iterable<Serializable> ids);
    void deleteAllById(Iterable<Serializable> ids);


//    ok
    boolean lock(String key);
//    ok
    void unlock(String key);

}
