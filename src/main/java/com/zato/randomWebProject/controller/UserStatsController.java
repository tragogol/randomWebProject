package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.UserStats;
import com.zato.randomWebProject.repository.UserStatsRepository;
import com.zato.randomWebProject.service.UserInteractionService;
import com.zato.randomWebProject.util.UserStatsChecker;
import com.zato.randomWebProject.util.UserStatsModelAssembler;
import com.zato.randomWebProject.util.UserStatsNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserStatsController {
  private final UserStatsRepository repository;
  private final UserStatsModelAssembler assembler;
  private final UserStatsChecker userChecker = new UserStatsChecker();

  public UserStatsController(UserStatsRepository repository, UserStatsModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("/userStats")
    public CollectionModel<EntityModel<UserStats>> all() {
      List<EntityModel<UserStats>> users = repository.findAll().stream() //
          .map(assembler::toModel) //
          .collect(Collectors.toList());

      return CollectionModel.of(users, linkTo(methodOn(UserStatsController.class).all()).withSelfRel());
    }

  @GetMapping("/userStats/{id}")
  public EntityModel<UserStats> one(@PathVariable Long id) {

    UserStats userStats = repository.findById(id) //
        .orElseThrow(() -> new UserStatsNotFoundException(id));

    return assembler.toModel(userStats);
  }

  @PostMapping("/userStats")
  public ResponseEntity<?> newUserStats(@RequestBody UserStats newUserStats) {

    if (userChecker.checkNewUser(newUserStats)) {
      EntityModel<UserStats> entityModel = assembler.toModel(repository.save(newUserStats));

      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    } else {
      return ResponseEntity
          .status(HttpStatus.NOT_ACCEPTABLE)
          .body("Bad parameters value");
    }


  }

  @PatchMapping("/userStats/{id1}/{id2}")
  public ResponseEntity<?> userAttack(@PathVariable Long id1, @PathVariable Long id2) {
    UserStats userStats1 = repository.findById(id1)
        .orElseThrow(() -> new UserStatsNotFoundException(id1));
    UserStats userStats2 = repository.findById(id2)
        .orElseThrow(() -> new UserStatsNotFoundException(id2));

    UserInteractionService.ChangeHealthValues(userStats1, userStats2, userChecker, repository);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body("Your stats: " + userStats1 + '\n' + "Enemy stats: " + userStats2);
  }
}
