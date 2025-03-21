package za.ump.scms.bict.user.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "UserPU")
    private EntityManager entityManager;

    @NotBlank(message = "First name should not be blank")
    private String name;

    private String address;
    private String phoneNumber;

    private Long id;

    // List to hold all users for display in the view
    private List<Users> allUsers;

    // Getter and Setter for name, address, phoneNumber, and id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Users> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }

    // Method to save a user with their details
    @Transactional
    public String saveUser() {
        // Create User and UserDetails objects
        Users user = new Users(name);
        UserDetails userDetails = new UserDetails();
        userDetails.setAddress(address);
        userDetails.setPhoneNumber(phoneNumber);
        
        // Set the relationship
        user.setUserDetails(userDetails);

        // Persist the objects
        entityManager.persist(userDetails);  // Persist UserDetails first
        entityManager.persist(user);         // Persist User (this will update the foreign key)

        // Refresh the list of all users after saving
        loadAllUsers();

        return "userSaved.xhtml?faces-redirect=true";  // Redirect to a page showing the saved user
    }

    // Method to load all users from the database
    public void loadAllUsers() {
        this.allUsers = entityManager.createQuery("SELECT u FROM Users u", Users.class).getResultList();
    }

    // Optional: Method to load an existing user by ID (if needed for updating)
    public void loadUser(Long userId) {
        Users user = entityManager.find(Users.class, userId);
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }

    // Load users when the bean is initialized
    @PostConstruct
    public void init() {
        loadAllUsers();
    }
}
