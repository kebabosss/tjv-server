package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.repository.DirectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DirectorServiceTest {

    @Autowired
    DirectorServices directorServices;

    @MockBean
    DirectorRepository directorRepository;

    @Test
    void directorCanBeCreated() {
        Director director = new Director();
        director.setId(1L);
        director.setName("Quentin Tarantino");
        director.setYearOfBirth(1963);

        Mockito.when(directorRepository.findAllByNameAndYearOfBirth("Quentin Tarantino", 1963))
                .thenReturn(List.of()); 
        Mockito.when(directorRepository.save(director)).thenReturn(director);

        Director createdDirector = directorServices.Create(director);

        Mockito.verify(directorRepository, Mockito.times(1)).save(director);
        Assertions.assertEquals("Quentin Tarantino", createdDirector.getName());
        Assertions.assertEquals(1963, createdDirector.getYearOfBirth());
    }

    @Test
    void duplicateDirectorCannotBeCreated() {
        Director director = new Director();
        director.setId(1L);
        director.setName("Quentin Tarantino");
        director.setYearOfBirth(1963);

        Mockito.when(directorRepository.findAllByNameAndYearOfBirth("Quentin Tarantino", 1963))
                .thenReturn(List.of(director));

        Assertions.assertThrows(IllegalArgumentException.class, () -> directorServices.Create(director));
        Mockito.verify(directorRepository, Mockito.never()).save(director);
    }

    @Test
    void directorCanBeDeleted() {
        Director director = new Director();
        director.setId(1L);
        director.setName("Steven Spielberg");

        Mockito.when(directorRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(directorRepository).deleteById(1L);

        directorServices.DeleteById(1L);

        Mockito.verify(directorRepository, Mockito.times(1)).deleteById(1L);
    }
}