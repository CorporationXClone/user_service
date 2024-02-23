package school.faang.user_service.filters.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.contact.Contact;
import school.faang.user_service.entity.contact.ContactType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContactPatternFilterTest {
    @InjectMocks
    private ContactPatternFilter contactPatternFilter;
    private static List<User> users;

    @BeforeAll
    static void init() {
        users = List.of(
                User.builder()
                        .id(1L)
                        .contacts(List.of(
                                Contact.builder()
                                        .contact("tupikdev")
                                        .type(ContactType.TELEGRAM)
                                        .build(),
                                Contact.builder()
                                        .contact("vk-1")
                                        .type(ContactType.VK)
                                        .build()))
                        .build(),
                User.builder()
                        .id(2L)
                        .contacts(List.of(
                                Contact.builder()
                                        .contact("something")
                                        .type(ContactType.TELEGRAM)
                                        .build(),
                                Contact.builder()
                                        .contact("vk-2")
                                        .type(ContactType.VK)
                                        .build()))
                        .build(),
                User.builder()
                        .id(3L)
                        .contacts(List.of(
                                Contact.builder()
                                        .contact("smth")
                                        .type(ContactType.TELEGRAM)
                                        .build(),
                                Contact.builder()
                                        .contact("vk-3")
                                        .type(ContactType.VK)
                                        .build()))
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(contactPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .contactPattern("   ")
                .build();

        assertFalse(contactPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .contactPattern("tupikdev")
                .build();
        List<User> result = contactPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}