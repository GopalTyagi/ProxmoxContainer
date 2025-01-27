package in.com.firewall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "in.com.firewall" })
public class FirewallTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirewallTestApplication.class, args);
	}
}