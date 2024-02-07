package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariffs;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
public interface TariffsService {

    Tariffs getById(int id);
    Tariffs getByCompany(Company company);

    List<Tariffs> getAll();

    void create(Tariffs tariffs);

    void update(Tariffs tariffsToUpdate, User user);

    void delete(int tariffId, User user);

}
