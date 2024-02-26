package school.faang.user_service.mapper;

import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    public void toDto_checkCorrectWork() {
        User userEntity = User.builder()
                .id(1L)
                .username("username")
                .email("email")
                .build();

        UserDto userDto = userMapper.toDto(userEntity);

        assertNotNull(userDto);

        assertEquals(1L, userDto.getId());
        assertEquals("username", userDto.getUsername());
        assertEquals("email", userDto.getEmail());
    }

    @Test
    public void toEntity_checkCorrectWork(){
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("username")
                .email("email")
                .build();

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);

        assertEquals(1L, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("email", user.getEmail());
    }
}