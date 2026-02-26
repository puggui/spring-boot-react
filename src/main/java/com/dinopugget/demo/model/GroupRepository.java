package com.dinopugget.demo.model;

// interface from Spring Data JPA, auto generate database logic
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name); // custom query method
}
// methods when exten JpaRepository include 
// save(Group entity)
// findById(Long id)
// findAll()
// deleteById(Long id)
// count()
// existsById(Long id)
// etc...