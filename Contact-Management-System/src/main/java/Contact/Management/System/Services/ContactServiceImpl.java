package Contact.Management.System.Services;
import Contact.Management.System.Entity.Contact;
import Contact.Management.System.Repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    private Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact save(Contact contact) {
        log.info("Inside ContactServiceImpl save() method contact data =" + contact);
        String email = contact.getEmail();
        String phoneNumber = contact.getPhoneNumber();
        Optional<Contact> contactResponse = contactRepository.findByEmailAndPhoneNumber(email, phoneNumber);
        if(contactResponse.isPresent()){
            throw new RuntimeException("Contact records already create with this phone number : " + phoneNumber + "and email : "+ email);
        }else{
            Contact save = contactRepository.save(contact);
            return save;
        }
    }

    @Override
    public List<Contact> getContact(String searchText) {
        log.info("Inside ContactServiceImpl getContact() method : ");
        List<Contact> contactList = contactRepository.searchContacts(searchText);
        return contactList;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.info("Inside ContactServiceImpl findById() method : ");
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isEmpty()){
            throw new RuntimeException("Contact Records not found with given id : "+ id);
        }
        return contact;
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found id =" + id));
        contactRepository.deleteById(id);
    }

    @Override
    public Contact update(Contact contact, Long id) {
        Optional<Contact> contactResponse = contactRepository.findById(id);
        if(contactResponse.isEmpty()){
            throw new RuntimeException("Record not found with given id : " +id);
        }
        Contact contact1 = contactResponse.get();
        Contact contactData = null;
        if (Objects.nonNull(contact.getFirstName())) {
            contact1.setFirstName(contact.getFirstName());
        }
        if (Objects.nonNull(contact.getLastName())) {
            contact1.setLastName(contact.getLastName());
        }
        if (Objects.nonNull(contact.getEmail())) {
            contact1.setEmail(contact.getEmail());
        }
        if (Objects.nonNull(contact.getPhoneNumber())) {
            contact1.setPhoneNumber(contact.getPhoneNumber());
        }
        contactData = contactRepository.save(contact1);
        return contactData;
    }
}
