package userstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userstore.dto.UserDto;
import userstore.model.User;
import userstore.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getById(Long id) {
        log.debug("getById: "+id);
        return userRepository.findById(id).map(this::toDto).orElse(null);
    }

    public List<UserDto> getAll() {
        log.debug("getAll");
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto userDto) {
        log.debug("Save: "+userDto);
        if(userDto.getRegistrationDate() == null)
        {
            userDto.setRegistrationDate(new Date());
        }
        return toDto(userRepository.save(toEntity(userDto)));
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getCity(), user.getRegistrationDate() == null ? null : new Date(user.getRegistrationDate().getTime()));
    }

    private User toEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getCity(), userDto.getRegistrationDate() == null ? null : new java.sql.Date(userDto.getRegistrationDate().getTime()));
    }

}
