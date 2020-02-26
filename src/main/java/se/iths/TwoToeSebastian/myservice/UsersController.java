package se.iths.TwoToeSebastian.myservice;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<UserData>> one(@PathVariable Integer id) {
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
