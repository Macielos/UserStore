package userstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import userstore.dto.AuthenticationData;
import userstore.service.AuthenticationService;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;

	@CrossOrigin
    @GetMapping()
    public boolean authenticate(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) {
        return authenticationService.authenticate(new AuthenticationData(username, password));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AuthenticationData authenticationData) {
        authenticationService.register(authenticationData);
    }
}
