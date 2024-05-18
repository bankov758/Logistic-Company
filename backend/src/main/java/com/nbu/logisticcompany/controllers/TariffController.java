package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffCreateDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffOutDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffUpdateDto;
import com.nbu.logisticcompany.mappers.TariffsMapper;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.nbu.logisticcompany.utils.DataUtil.getDefaultMessages;

@RestController
@RequestMapping("/api/tariffs")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TariffController {

    private final TariffsService tariffsService;
    private final AuthenticationHelper authenticationHelper;
    private final TariffsMapper tariffsMapper;

    public TariffController(TariffsService tariffsService, AuthenticationHelper authenticationHelper,
                            TariffsMapper tariffsMapper) {
        this.tariffsService = tariffsService;
        this.authenticationHelper = authenticationHelper;
        this.tariffsMapper = tariffsMapper;
    }

    @GetMapping
    public List<TariffOutDto> getAll(HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return tariffsService.getAll().stream()
                .map(tariffsMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TariffOutDto getById(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return tariffsMapper.ObjectToDTO(tariffsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(HttpSession session,
                                    @Valid @RequestBody TariffCreateDto tariffCreateDto, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
        try {
            User creator = authenticationHelper.tryGetUser(session);
            Tariff tariff = tariffsMapper.DTOtoObject(tariffCreateDto);
            tariffsService.create(tariff, creator);
            return ResponseEntity.ok().body(tariffCreateDto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(HttpSession session,
                                    @Valid @RequestBody TariffUpdateDto tariffUpdateDto, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
        User updater = authenticationHelper.tryGetUser(session);
        Tariff tariff = tariffsMapper.UpdateDTOtoTariffs(tariffUpdateDto);
        tariffsService.update(tariff, updater);
        return ResponseEntity.ok().body(tariffUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(HttpSession session, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(session);
        tariffsService.delete(id, user);
    }

}
