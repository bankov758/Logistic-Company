package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
public interface TariffsService {

    Tariff getById(int id);
    Tariff getByCompany(Company company);

    List<Tariff> getAll();

    void create(Tariff tariff);

    void update(Tariff tariffToUpdate, User user);

    void delete(int tariffId, User user);

}
