package se.iths.TwoToeSebastian.myservice;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/usersData")   // link from insomnia after 8080/
@RestController
public class UsersController {

    final UserDataRepository repository;
    private final UserDataModelAssembler assembler;

    public UsersController(UserDataRepository in, UserDataModelAssembler in2) {
        this.repository = in;
        this.assembler = in2;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<UserData>> one(@PathVariable Integer id) {
        log.info("One person called");
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public CollectionModel<EntityModel<UserData>> all() {
        log.info("All persons called");
        return assembler.toCollectionModel(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserData>> createPerson(@RequestBody UserData user) {
        log.info("Created " + user);

        if(repository.findById(user.getId()).isPresent()) {                                                                                     // if id exist, check for not overwrite
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        var p = repository.save(user);                                                                                                       // Sparar in användare från request body in i lista i databas
        log.info("Saved to repository " + p);
        var entityModel = assembler.toModel(p);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")                                                                                                                    // uppdate  if pressent
    ResponseEntity<EntityModel<UserData>> replacePerson(@RequestBody UserData userIn, @PathVariable Integer id) {

        if(repository.findById(id).isPresent()){
            log.info("IF");
            var p = repository.findById(id)
                    .map(existingUser -> {
                        existingUser.setUserName(userIn.getUserName());
                        existingUser.setRealName(userIn.getRealName());
                        existingUser.setCity((userIn.getCity()));
                        existingUser.setIncome(userIn.getIncome());
                        existingUser.setInRelationship(userIn.inRelationship);
                        repository.save(existingUser);
                        return existingUser;})
                    .get();
            var entityModel = assembler.toModel(p);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else{
            log.info("ELSE!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<EntityModel<UserData>> modifyUser(@RequestBody UserData updatedUser, @PathVariable Integer id){
        if(repository.findById(id).isPresent()){
            var p = repository.findById(id)
                    .map(newUser -> {
                        if(updatedUser.getUserName() != null)
                            newUser.setUserName(updatedUser.getUserName());
                        if(updatedUser.getRealName() != null)
                            newUser.setRealName(updatedUser.getRealName());
                        if(updatedUser.getCity() != null)
                            newUser.setCity(updatedUser.getCity());
                        if(updatedUser.getIncome() != newUser.getIncome())
                            newUser.setIncome(updatedUser.getIncome());
                        if(updatedUser.isInRelationship() != newUser.isInRelationship())
                            newUser.setInRelationship(updatedUser.isInRelationship());
                        repository.save(newUser);
                        return newUser;}).get();
            var entityModel = assembler.toModel(p);
            log.info("IDnr: " + p.getId() + " modified!");
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else {
            log.info("Wrong ID");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<UserData>> deletePerson(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            log.info("User deleted with id " + id);
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
