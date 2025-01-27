package in.com.firewall;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FirewallRepo extends JpaRepository<FirewallRule, Long> {
}
