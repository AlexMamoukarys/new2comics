package com.example.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.controller.exceptions.UserNotFoundException;
import com.example.cms.controller.exceptions.PublisherNotFoundException;
import com.example.cms.controller.exceptions.CharacterNotFoundException;
import com.example.cms.controller.exceptions.GenreNotFoundException;
import com.example.cms.controller.exceptions.VolumeNotFoundException;
import com.example.cms.controller.exceptions.PowerNotFoundException;
import com.example.cms.controller.exceptions.TeamNotFoundException;

import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Power;
import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.PreferredPublisher;
import com.example.cms.model.entity.PreferredPower;
import com.example.cms.model.entity.PreferredTeam;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.LikedVolumeRepository;
import com.example.cms.model.repository.PowerRepository;
import com.example.cms.model.repository.SavedVolumeRepository;
import com.example.cms.model.repository.TeamRepository;
import com.example.cms.model.repository.PreferredGenreRepository;
import com.example.cms.model.repository.PreferredCharacterRepository;
import com.example.cms.model.repository.PreferredPublisherRepository;
import com.example.cms.model.repository.PreferredPowerRepository;
import com.example.cms.model.repository.PreferredTeamRepository;
import com.example.cms.model.repository.PublisherRepository;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final LikedVolumeRepository likedVolumeRepository;
    @Autowired
    private final SavedVolumeRepository savedVolumeRepository;
    @Autowired
    private final PreferredGenreRepository preferredGenreRepository;
    @Autowired
    private final PreferredCharacterRepository preferredCharacterRepository;
    @Autowired
    private final PreferredPublisherRepository preferredPublisherRepository;
    @Autowired
    private final PreferredPowerRepository preferredPowerRepository;
    @Autowired
    private final PreferredTeamRepository preferredTeamRepository;
    @Autowired
    private final VolumeRepository volumeRepository;
    @Autowired
    private final GenreRepository genreRepository;
    @Autowired
    private final CharacterRepository characterRepository;
    @Autowired
    private final PublisherRepository publisherRepository;
    @Autowired
    private final PowerRepository powerRepository;
    @Autowired
    private final TeamRepository teamRepository;

    public UserController(UserRepository repository,
                          LikedVolumeRepository likedVolumeRepository,
                          SavedVolumeRepository savedVolumeRepository,
                          PreferredGenreRepository preferredGenreRepository,
                          PreferredCharacterRepository preferredCharacterRepository,
                          PreferredPublisherRepository preferredPublisherRepository,
                          PreferredPowerRepository preferredPowerRepository,
                          PreferredTeamRepository preferredTeamRepository,
                          VolumeRepository volumeRepository,
                          GenreRepository genreRepository,
                          CharacterRepository characterRepository,
                          PublisherRepository publisherRepository,
                          PowerRepository powerRepository,
                          TeamRepository teamRepository) {
        this.repository = repository;
        this.likedVolumeRepository = likedVolumeRepository;
        this.savedVolumeRepository = savedVolumeRepository;
        this.preferredGenreRepository = preferredGenreRepository;
        this.preferredCharacterRepository = preferredCharacterRepository;
        this.preferredPublisherRepository = preferredPublisherRepository;
        this.preferredPowerRepository = preferredPowerRepository;
        this.preferredTeamRepository = preferredTeamRepository;
        this.volumeRepository = volumeRepository;
        this.genreRepository = genreRepository;
        this.characterRepository = characterRepository;
        this.publisherRepository = publisherRepository;
        this.powerRepository = powerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/users")
    List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Long userId) {
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
    
    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // GET endpoints
    @GetMapping("/users/{id}/likedvolumes")
    List<LikedVolume> getLikedVolumes(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getLikedVolumes();
    }

    @GetMapping("/users/{id}/savedvolumes")
    List<SavedVolume> getSavedVolumes(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getSavedVolumes();
    }

    @GetMapping("/users/{id}/preferredpublishers")
    List<PreferredPublisher> getPreferredPublishers(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getPreferredPublishers();
    }

    @GetMapping("/users/{id}/preferredcharacters")
    List<PreferredCharacter> getPreferredCharacters(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getPreferredCharacters();
    }

    @GetMapping("/users/{id}/preferredgenres")
    List<PreferredGenre> getPreferredGenres(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getPreferredGenres();
    }

    @GetMapping("/users/{id}/preferredpowers")
    List<PreferredPower> getPreferredPowers(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getPreferredPowers();
    }

    @GetMapping("/users/{id}/preferredteams")
    List<PreferredTeam> getPreferredTeams(@PathVariable("id") Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getPreferredTeams();
    }

    // PUT endpoints
    @PutMapping("/users/{userId}/preferredpublishers/{publisherId}")
    PreferredPublisher addPreferredPublisher(@PathVariable("userId") Long userId,
                                             @PathVariable("publisherId") Long publisherId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new PublisherNotFoundException(publisherId));
        PreferredPublisher preferredPublisher = new PreferredPublisher(user, publisher);
        return preferredPublisherRepository.save(preferredPublisher);
    }

    @PutMapping("/users/{userId}/preferredcharacters/{characterId}")
    PreferredCharacter addPreferredCharacter(@PathVariable("userId") Long userId,
                                             @PathVariable("characterId") Long characterId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException(characterId));
        PreferredCharacter preferredCharacter = new PreferredCharacter(user, character);
        return preferredCharacterRepository.save(preferredCharacter);
    }

    @PutMapping("/users/{userId}/preferredgenres/{genreId}")
    PreferredGenre addPreferredGenre(@PathVariable("userId") Long userId,
                                     @PathVariable("genreId") Long genreId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
        PreferredGenre preferredGenre = new PreferredGenre(user, genre);
        return preferredGenreRepository.save(preferredGenre);
    }

    @PutMapping("/users/{userId}/likedvolumes/{volumeId}")
    LikedVolume addLikedVolume(@PathVariable("userId") Long userId,
                               @PathVariable("volumeId") Long volumeId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new VolumeNotFoundException(volumeId));
        LikedVolume likedVolume = new LikedVolume(user, volume);
        return likedVolumeRepository.save(likedVolume);
    }

    @PutMapping("/users/{userId}/savedvolumes/{volumeId}")
    SavedVolume addSavedVolume(@PathVariable("userId") Long userId,
                               @PathVariable("volumeId") Long volumeId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new VolumeNotFoundException(volumeId));
        SavedVolume savedVolume = new SavedVolume(user, volume);
        return savedVolumeRepository.save(savedVolume);
    }

    @PutMapping("/users/{userId}/preferredpowers/{powerId}")
    PreferredPower addPreferredPower(@PathVariable("userId") Long userId,
                                     @PathVariable("powerId") Long powerId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Power power = powerRepository.findById(powerId).orElseThrow(() -> new PowerNotFoundException(powerId));
        PreferredPower preferredPower = new PreferredPower(user, power);
        return preferredPowerRepository.save(preferredPower);
    }

    @PutMapping("/users/{userId}/preferredteams/{teamId}")
    PreferredTeam addPreferredTeam(@PathVariable("userId") Long userId,
                                   @PathVariable("teamId") Long teamId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        PreferredTeam preferredTeam = new PreferredTeam(user, team);
        return preferredTeamRepository.save(preferredTeam);
    }

    @PutMapping("/users/{userId}/preferredpublishers/remove/{publisherId}")
    PreferredPublisher removePreferredPublisher(@PathVariable("userId") Long userId,
                                                @PathVariable("publisherId") Long publisherId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        PreferredPublisher preferredPublisher = user.getPreferredPublishers().stream()
                .filter(item -> item.getPublisher().getId() == publisherId)
                .findFirst()
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));
        preferredPublisherRepository.delete(preferredPublisher);
        return preferredPublisher;
    }

    @PutMapping("/users/{userId}/preferredcharacters/remove/{characterId}")
    PreferredCharacter removePreferredCharacter(@PathVariable("userId") Long userId,
                                                @PathVariable("characterId") Long characterId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        PreferredCharacter preferredCharacter = user.getPreferredCharacters().stream()
                .filter(item -> item.getCharacter().getId() == characterId)
                .findFirst()
                .orElseThrow(() -> new CharacterNotFoundException(characterId));
        preferredCharacterRepository.delete(preferredCharacter);
        return preferredCharacter;
    }

    @PutMapping("/users/{userId}/preferredgenres/remove/{genreId}")
    PreferredGenre removePreferredGenre(@PathVariable("userId") Long userId,
                                        @PathVariable("genreId") Long genreId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        PreferredGenre preferredGenre = user.getPreferredGenres().stream()
                .filter(item -> item.getGenre().getId() == genreId)
                .findFirst()
                .orElseThrow(() -> new GenreNotFoundException(genreId));
        preferredGenreRepository.delete(preferredGenre);
        return preferredGenre;
    }

    @PutMapping("/users/{userId}/likedvolumes/remove/{volumeId}")
    LikedVolume removeLikedVolume(@PathVariable("userId") Long userId,
                                  @PathVariable("volumeId") Long volumeId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        LikedVolume likedVolume = user.getLikedVolumes().stream()
                .filter(item -> item.getVolume().getId() == volumeId)
                .findFirst()
                .orElseThrow(() -> new VolumeNotFoundException(volumeId));
        likedVolumeRepository.delete(likedVolume);
        return likedVolume;
    }

    @PutMapping("/users/{userId}/savedvolumes/remove/{volumeId}")
    SavedVolume removeSavedVolume(@PathVariable("userId") Long userId,
                                  @PathVariable("volumeId") Long volumeId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        SavedVolume savedVolume = user.getSavedVolumes().stream()
                .filter(item -> item.getVolume().getId() == volumeId)
                .findFirst()
                .orElseThrow(() -> new VolumeNotFoundException(volumeId));
        savedVolumeRepository.delete(savedVolume);
        return savedVolume;
    }

    @PutMapping("/users/{userId}/preferredpowers/remove/{powerId}")
    PreferredPower removePreferredPower(@PathVariable("userId") Long userId,
                                        @PathVariable("powerId") Long powerId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        PreferredPower preferredPower = user.getPreferredPowers().stream()
                .filter(item -> item.getPower().getId() == powerId)
                .findFirst()
                .orElseThrow(() -> new PowerNotFoundException(powerId));
        preferredPowerRepository.delete(preferredPower);
        return preferredPower;
    }

    @PutMapping("/users/{userId}/preferredteams/remove/{teamId}")
    PreferredTeam removePreferredTeam(@PathVariable("userId") Long userId,
                                      @PathVariable("teamId") Long teamId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        PreferredTeam preferredTeam = user.getPreferredTeams().stream()
                .filter(item -> item.getTeam().getId() == teamId)
                .findFirst()
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        preferredTeamRepository.delete(preferredTeam);
        return preferredTeam;
    }

    @DeleteMapping("/users/{userId}")                          // Delete
    void deleteUser(@PathVariable("userId") Long userId) {
        repository.deleteById(userId);
    }



}
