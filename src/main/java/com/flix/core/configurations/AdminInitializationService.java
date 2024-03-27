package com.flix.core.configurations;

import com.flix.core.models.entities.Channel;
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

    this.addChannel();
  }

  private void addChannel() {
    Query query = new Query(Criteria.where("tag").is("@jesus_copy"));
    if (!mongoTemplate.exists(query, Channel.class)) {
      Channel newChannel = new Channel();
      newChannel.setName("JesusCopy");
      newChannel.setMainLink("https://www.youtube.com/@jesus_copy");
      newChannel.setLogoLink(
          "https://yt3.googleusercontent.com/CpcJxo8rlbNV7eWDmEuONuUM2iJJYrn2zIL5Dw_7cjKPrtq50SVNSgiPR9ZdE2ea4AJn_Q0=s176-c-k-c0x00ffffff-no-rj");
      newChannel.setBackgroundLink(
          "https://yt3.googleusercontent.com/afj4cRKTdvKIoi8ZU7Gk3Ldb_mrqlZV3UeD-FVhtl-0GDhyQPRYmUQxnh8MRRIZfa__eZl6g=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj");
      newChannel.setTag("@jesus_copy");
      mongoTemplate.save(newChannel);
    }

    Query query2 = new Query(Criteria.where("tag").is("@PositivamentePodcast"));
    if (!mongoTemplate.exists(query2, Channel.class)) {
      Channel newChannel = new Channel();
      newChannel.setName("Positivamente Podcast");
      newChannel.setMainLink("https://www.youtube.com/@PositivamentePodcast");
      newChannel.setLogoLink(
          "https://yt3.googleusercontent.com/KatsIOJ4aYubY9Ou_dACcL-c8nBeO6ekEmG4aCpdqjlbB-1-RWn6kxcrAR2RMUfjiPWl4P0FFys=s176-c-k-c0x00ffffff-no-rj");
      newChannel.setBackgroundLink(
          "https://yt3.googleusercontent.com/YCID-k9HMUXrOG5ESzfd1yL8XJg3iotjvnfj3b0SEZ1Ar0bAnd8eO-baoCr2JSee67nwJe8KaQ=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj");
      newChannel.setTag("@PositivamentePodcast");
      mongoTemplate.save(newChannel);
    }

    Query query3 = new Query(Criteria.where("tag").is("@doisdedosdeteologia"));
    if (!mongoTemplate.exists(query2, Channel.class)) {
      Channel newChannel = new Channel();
      newChannel.setName("Dois Dedos de Teologia");
      newChannel.setMainLink("https://www.youtube.com/@doisdedosdeteologia");
      newChannel.setLogoLink(
              "https://yt3.googleusercontent.com/ytc/AIdro_l4AVcXOP5PrqINv-UX3iPKJdFUwXm0mBWWMxXh7w=s176-c-k-c0x00ffffff-no-rj");
      newChannel.setBackgroundLink(
              "https://yt3.googleusercontent.com/1fNzxU4_ZntGQxEFb3EZobYqjceYePxjiUd5P8ccQxDcdL1V9yqiWtBlB6Yo_BVe6I9eQ57BlB4=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj");
      newChannel.setTag("@doisdedosdeteologia");
      mongoTemplate.save(newChannel);
    }
  }
}
