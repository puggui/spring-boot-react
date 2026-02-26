// REST controller, handle HTTP request
package com.dinopugget.demo.web;

import com.dinopugget.demo.model.Group;
import com.dinopugget.demo.model.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController // handle HTTP requests, convert return values to JSON
@RequestMapping("/api") // set base URL
class GroupController {

    // create logger to print message to console
    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    // dependency injection 
    private GroupRepository groupRepository;

    // inject repository here
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // GET requests (read)
    @GetMapping("/groups")
    Collection<Group> groups() {
        return groupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id) {
        // may return a Group or empty
        Optional<Group> group = groupRepository.findById(id);
        // if found return 200 OK with json body, else return 404 Not Found
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST requests (create)
    @PostMapping("/group")
    // @Valid triggers validation annotations inside Group
    // @RequestBody convert JSON into Group object
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        // insert result to database
        Group result = groupRepository.save(group);
        // return 201 Created as JSON
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    /// PUT request (update)
    @PutMapping("/group/{id}")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    // DELETE request
    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

/*
POST
http POST :8080/api/group name='Utah JUG' city='Salt Lake City' country=USA
GET
http :3000/api/groups
http :3000/api/group/1
PUT
http PUT :3000/api/group/1 id=1 name='Utah JUG' address='On the slopes'
DELETE
http DELETE :3000/api/group/1
*/