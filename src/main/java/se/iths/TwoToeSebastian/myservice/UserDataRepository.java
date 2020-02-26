package se.iths.TwoToeSebastian.myservice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

        UserData findByName(String name);
}
