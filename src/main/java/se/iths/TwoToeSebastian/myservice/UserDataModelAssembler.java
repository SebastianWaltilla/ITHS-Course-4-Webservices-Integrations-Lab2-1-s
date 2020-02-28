package se.iths.TwoToeSebastian.myservice;

import org.apache.catalina.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDataModelAssembler implements RepresentationModelAssembler<UserData, EntityModel<UserData>> {
    @Override
    public EntityModel<UserData> toModel(UserData user) {
        return new EntityModel<>(user,
                linkTo(methodOn(UsersController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).all()).withRel("users"));
    }
    @Override
    public CollectionModel<EntityModel<UserData>> toCollectionModel(Iterable<? extends UserData> entities) {
        var collection = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(collection,
                linkTo(methodOn(UsersController.class).all()).withSelfRel());
    }
}