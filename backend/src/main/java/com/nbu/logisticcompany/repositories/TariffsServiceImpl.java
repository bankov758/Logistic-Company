package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariffs;
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
    public Tariffs getById(int id) {
        return tariffsRepository.getById(id);
    }

    @Override
    public Tariffs getByCompany(Company company) {
        return tariffsRepository.getByField("company", company);
    }

    @Override
    public List<Tariffs> getAll() {
        return tariffsRepository.getAll();
    }

    @Override
    public void create(Tariffs tariffs) {
        boolean duplicateTariff = true;
        try{
            tariffsRepository.getByField("id",tariffs.getId());
        }
        catch (EntityNotFoundException e) {
            duplicateTariff = false;
        }
        tariffsRepository.create(tariffs);
    }

    @Override
    public void update(Tariffs tariffsToUpdate, User user) {
        tariffsRepository.update(tariffsToUpdate);
    }

    @Override
    public void delete(int tariffId, User user) {
        tariffsRepository.delete(tariffId);
    }
}
