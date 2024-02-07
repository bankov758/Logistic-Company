package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.CourierOutDto;
import com.nbu.logisticcompany.entities.dto.CourierRegisterDto;
import com.nbu.logisticcompany.entities.dto.CourierUpdateDto;
import com.nbu.logisticcompany.mappers.CourierMapper;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;
    private final AuthenticationHelper authenticationHelper;
    private final CourierMapper courierMapper;

    public CourierController(CourierService courierService, AuthenticationHelper authenticationHelper,
                             CourierMapper courierMapper) {
        this.courierService = courierService;
        this.authenticationHelper = authenticationHelper;
        this.courierMapper = courierMapper;
    }

    @GetMapping
    public List<CourierOutDto> getAll(@RequestHeader HttpHeaders headers,
                                      @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(headers);
        return courierService.getAll(search).stream()
                .map(courierMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourierOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return courierMapper.ObjectToDTO(courierService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CourierRegisterDto courierRegisterDto, BindingResult result) {
        ValidationUtil.validate(result);
        Courier courier = courierMapper.DTOtoObject(courierRegisterDto);
        courierService.create(courier);
        return ResponseEntity.ok().body(courierRegisterDto);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody CourierUpdateDto courierToUpdate, BindingResult result) {
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(headers);
        Courier courier = courierMapper.UpdateDTOtoCourier(courierToUpdate);
        courierService.update(courier, updater);
        return ResponseEntity.ok().body(courierToUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        courierService.delete(id, user);
    }

}
