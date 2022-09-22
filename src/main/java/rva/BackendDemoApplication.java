package rva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages= {"rva.ctrls"})
public class BackendDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDemoApplication.class, args);
	}

}
