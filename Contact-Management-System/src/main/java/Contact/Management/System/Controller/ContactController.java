package Contact.Management.System.Controller;

import Contact.Management.System.Entity.Contact;
import Contact.Management.System.Services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api-contact")
public class ContactController {
    private Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    // Create a new contact
    @PostMapping()
    public ResponseEntity<Object> createContact(@RequestBody Contact contact) throws Exception {
        try {
            log.info("Inside ContactController createContact() method contact data =" + contact);
            Contact contactResponse = this.contactService.save(contact);
            return ResponseEntity.ok(contactResponse);
        } catch (Exception ex) {
            log.error("Inside ContactController createContact()" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating contact: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContact(@PathVariable(value = "id") Long id, @RequestBody Contact contact )
    {
        try {
            log.info("Inside ContactController updateContact() method  id = "+id);
            Contact contactResponse  = contactService.update(contact,id );
            return ResponseEntity.ok(contactResponse);
        } catch (Exception ex) {
            log.error("Inside ContactController updateContact() method "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating contact: " + ex.getMessage());
        }
    }

    // Get all contacts
    @GetMapping()
    public ResponseEntity<Object> getContact(@RequestParam(value = "text",required = false) String searchText) {
        try {
            log.info("Inside ContactController getContact() method");
            if (!StringUtils.isEmpty(searchText)) {
                if(searchText.length() < 3){
                    throw new RuntimeException("Search key should contain at least 3 characters");
                }
            }
            List<Contact> pageableResponse = contactService.getContact(searchText);
            return ResponseEntity.ok(pageableResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Inside ContactController getContact() method"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting contact: " + ex.getMessage());
        }
    }

    // Get a contact by ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getContactById(@PathVariable(name = "id") Long id) throws Exception {
        try {
            log.info("Inside ContactController getContactById() method ");
            Optional<Contact> contact = contactService.findById(id);
            return ResponseEntity.ok(contact);
        }catch (Exception ex) {
            log.error("Inside ContactController getContactById()" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting contact: " + ex.getMessage());
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable(value = "id") Long id) {
        try {
            log.info("Inside ContactController deleteContact() method ");
            contactService.deleteContactById(id);
            return ResponseEntity.ok("Record Deleted Sucessfully");
        }
        catch (Exception ex) {
            log.error("Inside ContactController deleteContact() method" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete contact: " + ex.getMessage());
        }
    }
}
