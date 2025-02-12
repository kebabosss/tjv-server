package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.EntityWithID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<Entity extends EntityWithID<Key>, Key> {
    JpaRepository<Entity, Key> repository;

    public AbstractCrudService(JpaRepository<Entity, Key> repository) {
        this.repository = repository;
    }

    public Entity Create(Entity entity)
    {
        if(entity.getId() != null && repository.existsById(entity.getId()))
            throw new IllegalArgumentException();
        return repository.save(entity);
    }

    public Entity ReadById(Key id)
    {
        Optional<Entity> opt = repository.findById(id);
        if(opt.isEmpty())
            throw new IllegalArgumentException();
        return opt.get();
    }

    public List<Entity> ReadAll()
    {
        return repository.findAll();
    }

    public Entity Update(Entity entity)
    {
        if(entity.getId() != null && !repository.existsById(entity.getId()))
            throw new IllegalArgumentException();
        return repository.save(entity);
    }

    public void DeleteById(Key Id){
        if(!repository.existsById(Id))
            throw new IllegalArgumentException();
        repository.deleteById(Id);
    }

}
