package mk.ukim.finki.av1wp.web.rest;

import mk.ukim.finki.av1wp.model.Manufacturer;
import mk.ukim.finki.av1wp.service.ManufacturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/*api/manufacturers")
public class ManufacturerController {
    private ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<Manufacturer> findAll(){
        return manufacturerService.findAll();
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Manufacturer> findById(@PathVariable Long id){
        return manufacturerService.findById(id)
                .map(manufacturer -> ResponseEntity.ok().body(manufacturer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Manufacturer> save(@RequestParam String name, @RequestParam String address){
        return manufacturerService.save(name, address)
                .map(manufacturer -> ResponseEntity.ok().body(manufacturer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        manufacturerService.deleteById(id);
        if(manufacturerService.findById(id).isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
