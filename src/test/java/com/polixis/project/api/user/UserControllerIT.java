package com.polixis.project.api.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polixis.project.core.user.IUserService;
import com.polixis.project.core.user.User;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
        UserController.class,
        UserApiMapper.class,
        UserApiMapperImpl.class
})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @Test
    void should_return_status_code_200_on_get_all_users_endpoint() throws Exception {
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_list_of_users() throws Exception {
        UUID user1Id = UUID.randomUUID();
        User user1 = User.builder()
                .id(user1Id)
                .firstName("John")
                .lastName("Doe")
                .build();


        UUID user2Id = UUID.randomUUID();
        User user2 = User.builder()
                .id(user2Id)
                .firstName("Smith")
                .lastName("Wayne")
                .build();

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        MvcResult mvcResult = mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String mvcResponseAsString = mvcResult.getResponse().getContentAsString();

        List<User> mvcResponse = new ObjectMapper().readValue(mvcResponseAsString, new TypeReference<>() {
        });

        assertThat(mvcResponse)
                .asInstanceOf(InstanceOfAssertFactories.LIST)
                .containsExactlyInAnyOrder(user1, user2);
    }
}