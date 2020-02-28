package se.iths.TwoToeSebastian.myservice;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequestMapping("/api/v1/userdata")
@RestController
public class UsersController {

    final UserDataRepository repository;
    private final UserDataModelAssembler assembler;


    public UsersController(UserDataRepository in, UserDataModelAssembler in2) {
        this.repository = in;
        this.assembler = in2;
    }

    @GetMapping
    public CollectionModel<EntityModel<UserData>> all() {
        log.info("All persons called");
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<UserData>> one(@PathVariable Integer id) {
        log.info("One person called");
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserData> createPerson(@RequestBody UserData user) {
        var p = repository.save(user);          // Sparar in användare från request body in i lista i databas
        HttpHeaders headers = new HttpHeaders();        // skapar en header
        headers.setLocation(linkTo(UsersController.class).slash(p.getId()).toUri());    // autogenera ny user id,  //headers.add("Location", "/api/persons/" + p.getId());
        return new ResponseEntity<>(p, headers, HttpStatus.CREATED); // p body(user data in), headers vilka headers som skickas tillbaka, httpstatus.created (201 ok kod).
    }






}
