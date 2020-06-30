package org.share.odies.api;

import org.share.odies.vo.PageOf;
import org.share.odies.vo.PageResult;
import org.share.odies.vo.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

public interface JedisCurdRepository<T,ID>  {

    void save(T ro);
    T findById(ID id);
    boolean exists(ID id);
    void delete(ID id);
    long count();
    List<T> findAll();
    PageResult<T> findAll(PageOf pageOf);
    PageResult<T> findAll(PageOf pageOf, SortBy sortBy);
    void deleteAll();
    void saveAll(Iterable<T> ros);
    List<T> findAllById(Iterable<ID> ids);
    void deleteAllById(Iterable<ID> ids);



}
