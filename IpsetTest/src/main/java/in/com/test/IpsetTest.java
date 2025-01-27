package in.com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "in.com" })
public class IpsetTest {

	public static void main(String[] args) {
		SpringApplication.run(IpsetTest.class, args);
	}
}
