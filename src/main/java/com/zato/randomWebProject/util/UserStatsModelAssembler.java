package com.zato.randomWebProject.util;

import com.zato.randomWebProject.controller.UserStatsController;
import com.zato.randomWebProject.data.UserStats;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserStatsModelAssembler implements RepresentationModelAssembler<UserStats, EntityModel<UserStats>> {
  @Override
  public EntityModel<UserStats> toModel(UserStats userStats) {

    return EntityModel.of(userStats, //
        linkTo(methodOn(UserStatsController.class).one(userStats.getId())).withSelfRel(),
        linkTo(methodOn(UserStatsController.class).all()).withRel("userStats"));
  }
}
