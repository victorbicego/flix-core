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

    // Add some channels to test
    this.addChannels();
  }

  private void addChannels() {
    this.addSingleChannel(
        "JesusCopy",
        "https://www.youtube.com/@jesus_copy",
        "https://yt3.googleusercontent.com/CpcJxo8rlbNV7eWDmEuONuUM2iJJYrn2zIL5Dw_7cjKPrtq50SVNSgiPR9ZdE2ea4AJn_Q0=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/afj4cRKTdvKIoi8ZU7Gk3Ldb_mrqlZV3UeD-FVhtl-0GDhyQPRYmUQxnh8MRRIZfa__eZl6g=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@jesus_copy");

    this.addSingleChannel(
        "Positivamente Podcast",
        "https://www.youtube.com/@PositivamentePodcast",
        "https://yt3.googleusercontent.com/KatsIOJ4aYubY9Ou_dACcL-c8nBeO6ekEmG4aCpdqjlbB-1-RWn6kxcrAR2RMUfjiPWl4P0FFys=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/YCID-k9HMUXrOG5ESzfd1yL8XJg3iotjvnfj3b0SEZ1Ar0bAnd8eO-baoCr2JSee67nwJe8KaQ=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@PositivamentePodcast");

    this.addSingleChannel(
        "Dunamis Movement",
        "https://www.youtube.com/@DunamisMovement_",
        "https://yt3.googleusercontent.com/tJYX4d36o7rx8B0HXCaHTXKER53AEaLhyFnap2Em2Zm6iCUTkbvt6O_5Gd_O6PulGX1EztYh=s176-c-k-c0x00ffffff-no-rj",
        "    https://yt3.googleusercontent.com/xa0D_9otVSHjKGYM_4Pt8lCahbrMUEXzkLi7bhsCP-HQuDRyYa38n5s-jLfU-o6JdK_lxuld=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj\n",
        "@DunamisMovement_");

    this.addSingleChannel(
        "Dois Dedos de Teologia",
        "https://www.youtube.com/@doisdedosdeteologia",
        "https://yt3.googleusercontent.com/ytc/AIdro_l4AVcXOP5PrqINv-UX3iPKJdFUwXm0mBWWMxXh7w=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/1fNzxU4_ZntGQxEFb3EZobYqjceYePxjiUd5P8ccQxDcdL1V9yqiWtBlB6Yo_BVe6I9eQ57BlB4=w2560-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@doisdedosdeteologia");

    this.addSingleChannel(
        "Rodrigo Silva Arqueologia",
        "https://www.youtube.com/@RodrigoSilvaArqueologia",
        "https://yt3.googleusercontent.com/ytc/AIdro_n5SF6S-y7uYw69mxrs1fQUjmL_HiTttd1_dRZDww=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/PiQHoZYN8ePCHChG0KLtTtfswSKrlO5mfv8XgnzQQxX2t8PpMTOpQrn3n7pYaS6Y2lHx-GOEQA=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@RodrigoSilvaArqueologia");

    this.addSingleChannel(
        "Luciano Subirá",
        "https://www.youtube.com/@lucianosubira",
        "https://yt3.googleusercontent.com/ytc/AIdro_kOKjBo6Ikv2WgA3pvrPuiy2UTj4OwMvGDUS16hVA=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/sxzOUe1KfpjXXBwOri64Kybc6tm-dt8SZruPMABUeODlcFNWpIYcJTry09NFkck1KO8cDhNz=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@lucianosubira");

    this.addSingleChannel(
        "Angelo Bazzo",
        "https://www.youtube.com/@AngeloBazzo",
        "https://yt3.googleusercontent.com/mP0yLjLC5-tfD2Y0y5v8KG-_GWAWoBgG4CemsKPfgrbiwbN4reCKLM6zNDacT8gh7v48UHaFdQ=s176-c-k-c0x00ffffff-no-rj",
        "https://yt3.googleusercontent.com/h5BZYlH6QfVQC__XSu9usGha_qE0OM5F0y4_NSboMTT_mFU7MmgwbFiayCWIBcKEODO2sLvePg=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj",
        "@AngeloBazzo");
  }

  private void addSingleChannel(
      String channelName,
      String channelMainLink,
      String channelLogoLink,
      String channelBackgroundLink,
      String id) {
    Query query = new Query(Criteria.where("id").is(id));
    if (!mongoTemplate.exists(query, Channel.class)) {
      Channel newChannel = new Channel();
      newChannel.setName(channelName);
      newChannel.setMainLink(channelMainLink);
      newChannel.setLogoLink(channelLogoLink);
      newChannel.setBackgroundLink(channelBackgroundLink);
      newChannel.setId(id);
      mongoTemplate.save(newChannel);
    }
  }
}
