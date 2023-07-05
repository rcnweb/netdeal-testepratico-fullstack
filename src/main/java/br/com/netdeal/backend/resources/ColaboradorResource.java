package br.com.netdeal.backend.resources;

import br.com.netdeal.backend.domain.Colaborador;
import br.com.netdeal.backend.services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/colaboradores")
public class ColaboradorResource {

    @Autowired
    private ColaboradorService service;

    @GetMapping("/")
    public ResponseEntity<List<Colaborador>> findAll() {
        List<Colaborador> objList = service.findAll();
        return ResponseEntity.ok().body(objList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> find(@PathVariable Integer id) {
        Colaborador obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody Colaborador obj){
        obj = service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Colaborador obj,@PathVariable Integer id){
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}
