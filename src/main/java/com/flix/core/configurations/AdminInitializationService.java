package com.flix.core.configurations;

import com.flix.core.models.entities.User;
import com.flix.core.models.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

// Create ADMIN if it does not exist.
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminInitializationService {

  private final MongoTemplate mongoTemplate;

  @PostConstruct
  public void initializeAdminUser() {
    Query query = new Query(Criteria.where("username").is("admin"));
    if (!mongoTemplate.exists(query, User.class)) {
      User adminUser = new User();
      adminUser.setUsername("admin");
      adminUser.setPassword("$2a$10$k/L/Nc5iW.1hQZhnGxm2o.K7X49t2t9hpCheFP0SdsG1iknazt8H6");
      adminUser.setRole(Role.ADMIN);
      adminUser.setName("Admin");
      adminUser.setSurname("Master");
      adminUser.setAccountNonExpired(true);
      adminUser.setAccountNonLocked(true);
      adminUser.setCredentialNonExpired(true);
      adminUser.setEnabled(true);
      mongoTemplate.save(adminUser);
    }
  }
}
