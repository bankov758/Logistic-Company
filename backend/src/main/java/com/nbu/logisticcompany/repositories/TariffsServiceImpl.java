package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffsServiceImpl implements TariffsService {

    private final TariffsRepository tariffsRepository;

    @Autowired
    public TariffsServiceImpl(TariffsRepository tariffsRepository) {
        this.tariffsRepository = tariffsRepository;
    }


    @Override
    public Tariff getById(int id) {
        return tariffsRepository.getById(id);
    }

    @Override
    public Tariff getByCompany(Company company) {
        return tariffsRepository.getByField("company", company);
    }

    @Override
    public List<Tariff> getAll() {
        return tariffsRepository.getAll();
    }

    @Override
    public void create(Tariff tariff) {
        boolean duplicateTariff = true;
        try{
            tariffsRepository.getByField("id", tariff.getId());
        }
        catch (EntityNotFoundException e) {
            duplicateTariff = false;
        }
        tariffsRepository.create(tariff);
    }

    @Override
    public void update(Tariff tariffToUpdate, User user) {
        tariffsRepository.update(tariffToUpdate);
    }

    @Override
    public void delete(int tariffId, User user) {
        tariffsRepository.delete(tariffId);
    }
}
