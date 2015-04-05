package com.appdirect.integration.services;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.models.EditionCode;
import com.appdirect.integration.models.SubscriptionStatus;
import com.appdirect.integration.repositories.CompanyRepository;
import com.appdirect.integration.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private IdGenerator idGenerator;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, IdGenerator idGenerator) {
        this.companyRepository = companyRepository;
        this.idGenerator = idGenerator;
    }

    public String save(Company company) {
        company.setId(idGenerator.generateId());
        companyRepository.save(company);
        return company.getId();
    }

    public List<Company> getCompanies() {
        return newArrayList(companyRepository.findAll());
    }

    public void update(String identifier, EditionCode editionCode) {
        Company company = companyRepository.findOne(identifier);
        company.setEditionCode(editionCode);
        companyRepository.save(company);
    }

    public void delete(String identifier) {
        try {
            companyRepository.delete(identifier);
        } catch(EmptyResultDataAccessException ignored) {
        }
    }

    public void changeStatus(String identifier, SubscriptionStatus status) {
        Company company = companyRepository.findOne(identifier);
        company.setStatus(status);
        companyRepository.save(company);
    }
}
