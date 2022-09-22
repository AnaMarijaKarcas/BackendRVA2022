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
import rva.jpa.Student;
import rva.repositories.DepartmanRepository;
import rva.repositories.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD operacije"})
public class StudentRestController {

	@Autowired
	private StudentRepository studentRep;

	@Autowired
	private DepartmanRepository departmanRep;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("student")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudent(){
		return studentRep.findAll();
	}
	
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraća studenta sa odredjenim id-jem iz baze podataka")
	public Student getStudentById(@PathVariable ("id") Integer id) {
		return studentRep.getOne(id);
	}
	@GetMapping("studentByDepartman/{id}")
	@ApiOperation(value = "Vraća studente na osnovu id-ja departmana kojem pripadaju iz baze podataka")
	public Collection<Student> getStudentByDepartmanId(@PathVariable ("id") Integer id) {
		Departman d=departmanRep.getOne(id);
		return studentRep.findByDepartman(d);
	}
	@GetMapping("studentByPrezime/{prezime}")
	@ApiOperation(value = "Vraća studenta sa odredjenim prezimenom iz baze podataka")
	public Collection<Student>getStudentByPrezime(@PathVariable("prezime") String prezime){
		return studentRep.findByPrezimeOrderByIme(prezime);
	}
	
	@PostMapping("student")
	@ApiOperation(value = "Dodaje studenta u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student){
		if(!studentRep.existsById(student.getId())) {
			studentRep.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.CONFLICT);
	}
	@PutMapping("student")
	@ApiOperation(value = "Azurira studenta u bazi podataka")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student){
		if(studentRep.existsById(student.getId())) {
			studentRep.save(student);
				return new ResponseEntity<Student>(HttpStatus.OK);
			}
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}
	@DeleteMapping("student/{id}")
	@ApiOperation(value = "Brise studenta iz baze podataka")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id){
		if(studentRep.existsById(id)) {
			studentRep.deleteById(id);
				if(id==-100) {
					jdbcTemplate.execute("insert into student(id, ime, prezime, broj_indeksa, status, departman) values(-100, 'test', 'test', -120, -100, -100)");
				}
				return new ResponseEntity<Student>(HttpStatus.OK);
			}
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}
	
}
