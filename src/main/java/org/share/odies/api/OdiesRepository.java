package org.share.odies.api;

import org.share.odies.vo.PageOf;
import org.share.odies.vo.PageResult;
import org.share.odies.vo.SortBy;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface OdiesRepository<T,ID> {

//    ok
    void save(T ro);
//    ok
    T findById(ID id);
//    ok
    boolean exists(ID id);
//    ok
    void delete(ID id);
//    ok
    long count();

//  ok
    List<T> findAll();
    PageResult<T> findAll(PageOf pageOf);
    PageResult<T> findAll(PageOf pageOf, SortBy sortBy);


    void deleteAll();



    void saveAll(Iterable<T> ros);


    List<T> findAllById(Iterable<ID> ids);

    void deleteAllById(Iterable<ID> ids);



}
