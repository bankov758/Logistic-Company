package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.CourierOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierUpdateDto;
import com.nbu.logisticcompany.mappers.CourierMapper;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nbu.logisticcompany.utils.DataUtil.getDefaultMessages;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    public List<CourierOutDto> getAll(HttpSession session,
                                      @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(session);
        return courierService.getAll(search).stream()
                .map(courierMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourierOutDto getById(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return courierMapper.ObjectToDto(courierService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CourierRegisterDto courierRegisterDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        ValidationUtil.validate(result);
        Courier courier = courierMapper.DtoToObject(courierRegisterDto);
        courierService.create(courier);
        return ResponseEntity.ok().body(courierRegisterDto);
    }

    @PutMapping("/{courierId}/make-office-employee/{officeId}")
    public ResponseEntity<?> makeOfficeEmployee(HttpSession session, @PathVariable int courierId, @PathVariable int officeId) {
        User updater = authenticationHelper.tryGetUser(session);
        courierService.makeOfficeEmployee(courierId, officeId, updater);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/demote/{id}")
    public ResponseEntity<?> demoteToUser(HttpSession session, @PathVariable int id) {
        User updater = authenticationHelper.tryGetUser(session);
        courierService.demoteToUser(id, updater);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(HttpSession session,
                                    @Valid @RequestBody CourierUpdateDto courierToUpdate, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(session);
        Courier courier = courierMapper.UpdateDtoToCourier(courierToUpdate);
        courierService.update(courier, updater);
        return ResponseEntity.ok().body(courierToUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(HttpSession session, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(session);
        courierService.delete(id, user);
    }

}
