package userstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import userstore.dto.UserDto;
import userstore.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping(params = "id")
    public UserDto getById(Long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }
}
