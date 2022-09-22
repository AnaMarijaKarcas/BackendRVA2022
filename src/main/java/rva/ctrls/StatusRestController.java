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
import rva.jpa.Fakultet;
import rva.jpa.Status;
import rva.repositories.StatusRepository;

@CrossOrigin
@RestController
@Api(tags = {"Fakultet CRUD operacije"})
public class StatusRestController {

	@Autowired
	private StatusRepository statusRep;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	//za sql u javi
	
	@GetMapping("status")
	@ApiOperation(value = "Vraća kolekciju svih statusa iz baze podataka")
	public Collection<Status> getStatus(){
		return statusRep.findAll();
	}
	@GetMapping("status/{id}")
	@ApiOperation(value = "Vraća statuse sa odredjenim id-jem iz baze podataka")
	public Status getStatusById(@PathVariable("id")Integer id){
		return statusRep.getOne(id);
	}
	@GetMapping("statusOznaka/{oznaka}")
	@ApiOperation(value = "Vraća status sa odredjenom oznakom iz baze podataka")
	public Collection<Status> getStatusiByOznaka(@PathVariable("oznaka") String oznaka){
		return statusRep.findByOznakaContainingIgnoreCase(oznaka);
	}
	

	@PostMapping("status")
	@ApiOperation(value = "Dodaje status u bazu podataka")
	public ResponseEntity<Status> insertStatus(@RequestBody Status status){
		if(!statusRep.existsById(status.getId())) {
			statusRep.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
		}
		return new ResponseEntity<Status>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("status")
	@ApiOperation(value = "Azurira status iz baze podataka")
	public ResponseEntity<Status> updateStatus(@RequestBody Status status){
		if(statusRep.existsById(status.getId())) {
			statusRep.save(status);
				return new ResponseEntity<Status>(HttpStatus.OK);
			}
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("status/{id}")
	@ApiOperation(value = "Brise status iz baze podataka")
	public ResponseEntity<Status> deleteStatus(@PathVariable("id") Integer id){
		if(statusRep.existsById(id)) {
			statusRep.deleteById(id);
				if(id==-100) {
					jdbcTemplate.execute("insert into status(id, naziv, oznaka) values(-100, 'test', 'test')");
				}
				return new ResponseEntity<Status>(HttpStatus.OK);
			}
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
	}
	
		
}
