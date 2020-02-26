package se.iths.TwoToeSebastian.myservice;

import org.apache.catalina.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserDataModelAssembler implements RepresentationModelAssembler<UserData, EntityModel<UserData>> {

    //http://stateless.co/hal_specification.html

    @Override
    public EntityModel<UserData> toModel(UserData person) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<UserData>> toCollectionModel(Iterable<? extends UserData> entities) {
        return null;
    }
}