package example.jso.model.repository;

import example.jso.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("select u  from Users u where u.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);


}
