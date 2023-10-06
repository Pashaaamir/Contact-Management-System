package Contact.Management.System.Services;

import Contact.Management.System.Entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact save(Contact contact);

    List<Contact> getContact(String searchText);

    Optional<Contact> findById(Long id);

    void deleteContactById(Long id);

    Contact update(Contact contact, Long id);
}
