package com.flix.core.controllers.general;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.general.CategoryService;
import com.flix.core.services.general.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public UserDto getInfo() throws NotFoundException {
        return userService.getInfo();
    }

    @PutMapping("/update")
    public UserDto update(@RequestBody @Valid UserDto userDto) throws NotFoundException {
        return userService.update(userDto);
    }

    @DeleteMapping("/delete")
    public void delete() throws NotFoundException {
        userService.delete();
    }
}
