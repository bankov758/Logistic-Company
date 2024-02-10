package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;

import java.util.List;

public interface OfficeService {

    Office getById(int id);

    Office getByAddress(String address);

    Office getByCompany(Company company);

    List<Office> getAll();

    void create(Office office);

    void update(Office officeToUpdate, User user);

    void delete(int officeId, User user);

}
