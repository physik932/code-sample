package com.charter.enterprise.motd;

import com.charter.enterprise.motd.repository.MotdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/")
@RestController
public class MotdController {

    @Autowired
    private MotdRepository motdRepository;

    @GetMapping
    public String getMotd() {
        Optional<Motd> currentMotd = motdRepository.findById(1);
        String motd = null;

        if (currentMotd.isPresent())
            motd = currentMotd.get().getMotd();

        return motd;
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateMotd(@RequestBody String motd) {
        Optional<Motd> motdOptional = motdRepository.findById(1);

        if (!motdOptional.isPresent())
            return ResponseEntity.notFound().build();

        Motd updatedMotd = new Motd(1, motd);

        motdRepository.save(updatedMotd);

        return ResponseEntity.ok("Motd was updated!");
    }
}
