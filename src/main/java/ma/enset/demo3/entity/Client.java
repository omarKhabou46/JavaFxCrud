package ma.enset.demo3.entity;

public class Client {
    private Long id;
    private String lastName;
    private String firstName;
    private int age;

    public Client() {
        this.id = 0L;
        this.lastName="";
        this.firstName="";
        this.age=0;
    }

    public Client(String lastName, String firstName, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
