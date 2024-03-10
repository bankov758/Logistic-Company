package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

    Office getById(int id);

    Office getByAddress(String address);

    List<Office> filter(Optional<String> address, Optional<Integer> companyId, Optional<String> sort);

    List<Office> getAll();

    void create(Office office, User creator);

    void update(Office officeToUpdate, User updater);

    void delete(int officeId, User destroyer);

}
