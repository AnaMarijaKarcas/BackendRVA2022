package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Departman;
import rva.jpa.Fakultet;
import rva.repositories.DepartmanRepository;

@CrossOrigin
@RestController
@Api(tags = {"Departman CRUD operacije"})
public class DepartmanRestController {
	
	@Autowired
	private DepartmanRepository departmanRep;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("departman")
	@ApiOperation(value = "Vraća kolekciju svih departmana iz baze podataka")
	public Collection<Departman> getDepartmani(){
		return departmanRep.findAll();
	}
	
	@GetMapping("departman/{id}")
	@ApiOperation(value = "Vraća departman sa odredjenim id-jem iz baze podataka")
	public Departman getDepartman(@PathVariable("id") Integer id) {
		return departmanRep.getOne(id);
	}
	
	@GetMapping("departmanNaziv/{naziv}")
	@ApiOperation(value = "Vraća departman sa odredjenim nazivom iz baze podataka")
	public Collection<Departman> getDepartmaniByNaziv(@PathVariable("naziv") String naziv){
		return departmanRep.findByNazivContainingIgnoreCase(naziv);
	}

	@PostMapping("departman")
	@ApiOperation(value = "Dodaje departman u bazu podataka")
	public ResponseEntity<Departman> insertDepartman(@RequestBody Departman departman){
		if(!departmanRep.existsById(departman.getId())) {
			departmanRep.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		return new ResponseEntity<Departman>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("departman")
	@ApiOperation(value = "Azurira departman iz baze podataka")
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
		if(departmanRep.existsById(departman.getId())) {
			departmanRep.save(departman);
				return new ResponseEntity<Departman>(HttpStatus.OK);
			}
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("departman/{id}")
	@ApiOperation(value = "Brise departman iz baze podataka")
	public ResponseEntity<Departman> deleteDepartman(@PathVariable("id") Integer id){
		if(departmanRep.existsById(id)) {
				departmanRep.deleteById(id);
				if(id==-100) {
					jdbcTemplate.execute("insert into departman(id, naziv, oznaka, fakultet) values(-100, 'test', 'test',-100)");
				}
				return new ResponseEntity<Departman>(HttpStatus.OK);
			}
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
	}
}
