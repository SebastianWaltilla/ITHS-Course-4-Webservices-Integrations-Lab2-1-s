package se.iths.TwoToeSebastian.myservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(UsersController.class)
@Slf4j
@Import({UserDataModelAssembler.class})
class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserDataRepository repository;
    @BeforeEach
    void setup(){
        when(repository.findAll()).thenReturn(List.of(new UserData(1, "Anton", "Anton Johansson", "Mölndal", 10000, true),
                new UserData(2,"Sebbe", "Sebastian Waltilla", "Fjärås", 60000, true),
                new UserData(3,"Jonte", "Jonathan Holm", "Umeå", 40000, false)));
        when(repository.findById(1)).thenReturn(Optional.of(new UserData(1, "Anton", "Anton Johansson", "Mölndal", 10000, true)));
        when(repository.save(any(UserData.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            var u = (UserData) args[0];
            return new UserData(1, u.getUserName(),u.getRealName(),u.getCity(),u.getIncome(),u.isInRelationship());
        });
    }





    @Test
    @DisplayName("Calls GET method with url /api/v1/usersData/1")
    void getOnePersonWithValidIdOne() throws Exception {
        mockMvc.perform(
                get("/api/v1/usersData/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/usersData/1")));
    }


    @Test
    void getAllReturnsListOfAllPersons() throws Exception {
        mockMvc.perform(
                get("/api/v1/usersData").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/usersData")))
                .andExpect(jsonPath("_embedded.userDataList[0].userName", is("Anton")));
        //Build json paths with: https://jsonpath.com/
    }


    @Test
    void addNewPersonWithPostReturnsCreatedPerson() throws Exception {
        mockMvc.perform(
                post("/api/v1/usersData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"userName\":\"Innan JONANNA\",\"realName\":\"Johanna Svallingson\",\"city\":\"goteborg\",\"income\":10000,\"inRelationship\":true}"))
                .andExpect(status().isCreated());
    }


    @Test
    void addPutPersonWithPostReturnsCreatedPerson() throws Exception {
        mockMvc.perform(
                put("/api/v1/usersData/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"userName\":\"Efter put\",\"realName\":\"Anton Johansson Plopp\",\"city\":\"goteborg\",\"income\":10000,\"inRelationship\":true}"))
                .andExpect(status().isOk());
    }





}