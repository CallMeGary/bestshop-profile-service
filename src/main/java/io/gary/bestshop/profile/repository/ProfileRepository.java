package io.gary.bestshop.profile.repository;

import io.gary.bestshop.profile.domain.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByEmail(String email);
}
