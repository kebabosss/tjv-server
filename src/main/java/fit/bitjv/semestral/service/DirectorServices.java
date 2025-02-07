package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.repository.DirectorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorServices extends AbstractCrudService<Director,Long> {
    public DirectorServices(DirectorRepository repository) {
        super(repository);
    }
}
