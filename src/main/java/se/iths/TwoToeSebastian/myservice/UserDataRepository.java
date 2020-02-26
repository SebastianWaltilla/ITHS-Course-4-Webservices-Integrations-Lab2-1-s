package se.iths.TwoToeSebastian.myservice;
import org.springframework.data.jpa.repository.JpaRepository;


// use JpaRepository for get methods like findBy*name* or findBy*income*
// använder userdata och dess id
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
        UserData findByName(String name);
}
