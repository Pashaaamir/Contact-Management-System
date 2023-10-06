package Contact.Management.System.Repository;

import Contact.Management.System.Entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.email = :email AND c.phoneNumber = :phoneNumber")
    Optional<Contact> findByEmailAndPhoneNumber(@Param("email") String email, @Param("phoneNumber") String phoneNumber);


    @Query("SELECT c FROM Contact c WHERE :searchText IS NULL OR c.email LIKE %:searchText% "
            + "OR CONCAT(c.firstName, ' ', c.lastName) LIKE %:searchText% "
            + "OR c.lastName LIKE %:searchText% ")
    List<Contact> searchContacts(@Param("searchText") String searchText);


}
