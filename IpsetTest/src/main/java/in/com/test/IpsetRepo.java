package in.com.test;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IpsetRepo extends JpaRepository<IpSet, String> {
}
