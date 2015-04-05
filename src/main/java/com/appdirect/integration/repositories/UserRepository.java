package com.appdirect.integration.repositories;

import com.appdirect.integration.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);

    UserDetails findByOpenId(String openId);

    @Query("delete from User u where u.company.id=:companyId")
    void deleteAllFromCompany(@Param("companyId") String accountIdentifier);
}
