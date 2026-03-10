package com.example.cms.controller;

import com.example.cms.controller.dto.VolumeGenreDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.repository.VolumeGenreRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VolumeGenreController {
    @Autowired
    private final VolumeGenreRepository repository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private GenreRepository genreRepository;

    public VolumeGenreController(VolumeGenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/volume_genre")
    List<VolumeGenre> retrieveAllVolumeGenre() {
        return repository.findAll();
    }

    @PostMapping("/volume_genre")
    VolumeGenre createMark(@RequestBody VolumeGenreDto volumeGenreDto) {

        Volume volume = volumeRepository.findById(volumeGenreDto.getVolumeId()).orElseThrow(
                () -> new VolumeNotFoundException(volumeGenreDto.getVolumeId()));

        Genre genre = genreRepository.findById(volumeGenreDto.getGenreId()).orElseThrow(
                () -> new GenreNotFoundException(volumeGenreDto.getGenreId()));

        VolumeGenreKey volumeGenreKey = new VolumeGenreKey(volumeGenreDto.getVolumeId(), volumeGenreDto.getGenreId());
        VolumeGenre newVolumeGenre = new VolumeGenre();

        newVolumeGenre.setVolumeGenreId(volumeGenreKey);

        newVolumeGenre.setVolume(volume);
        newVolumeGenre.setGenre(genre);

        return repository.save(newVolumeGenre);
    }

    @DeleteMapping("/volume_genre/{volumeId}/{genreId}")
    void deleteCourseMark(@PathVariable("volumeId") long volumeId, @PathVariable("genreId") Long genreId) {
        VolumeGenreKey volumeGenreKey = new VolumeGenreKey(volumeId, genreId);
        repository.deleteById(volumeGenreKey);
    }

}
