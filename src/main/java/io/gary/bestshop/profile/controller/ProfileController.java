package io.gary.bestshop.profile.controller;

import io.gary.bestshop.profile.domain.Profile;
import io.gary.bestshop.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("profiles")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping
    public List<Profile> getProfiles() {
        return profileService.getProfiles();
    }

    @PostMapping
    public Profile createProfile(@RequestBody @Valid Profile Profile) {
        return profileService.createProfile(Profile);
    }

    @PutMapping("{username}")
    public Profile updateProfile(@PathVariable("username") String username, @RequestBody @Valid Profile Profile) {
        return profileService.updateProfile(username, Profile);
    }

    @GetMapping("{username}")
    public Profile getProfile(@PathVariable("username") String username) {
        return profileService.getProfile(username);
    }

    @DeleteMapping("{username}")
    public void deleteProfile(@PathVariable("username") String username) {
        profileService.deleteProfile(username);
    }

}
