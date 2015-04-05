package com.appdirect.integration.repositories;

import com.appdirect.integration.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    UserDetails findByOpenId(String openId);

    @Modifying
    @Transactional
    @Query("delete from User u where u.openId=:openId")
    void deleteByOpenId(@Param("openId") String openId);
}
