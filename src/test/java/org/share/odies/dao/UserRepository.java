package org.share.odies.dao;

import org.share.odies.entity.User;
import org.share.odies.service.DefaultJedisRepository;
import org.springframework.stereotype.Repository;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description:
 */
@Repository
public class UserRepository extends DefaultJedisRepository<User,Long> {



}
