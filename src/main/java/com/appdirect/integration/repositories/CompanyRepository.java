package com.appdirect.integration.repositories;

import com.appdirect.integration.models.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {
}
