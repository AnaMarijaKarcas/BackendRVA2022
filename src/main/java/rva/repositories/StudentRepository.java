package rva.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Departman;
import rva.jpa.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	Collection<Student> findByDepartman(Departman d);
	Collection<Student> findByPrezimeOrderByIme(String prezime);
	
	
}

