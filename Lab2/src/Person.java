import java.util.Date;
import java.util.Objects;

/**
 * Represents a person in the system.
 * Base class for both students and teachers.
 */
public class Person {
    private String name;
    private Date dateOfBirth;

    /**
     * Default constructor for Person.
     */
    public Person() {
    }

    /**
     * Constructor for Person with name and date of birth.
     *
     * @param name        The name of the person
     * @param dateOfBirth The date of birth of the person
     */
    public Person(String name, Date dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the name of the person.
     *
     * @return The name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the date of birth of the person.
     *
     * @return The date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the person.
     *
     * @param dateOfBirth The date of birth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Checks if this person is equal to another object.
     * Two persons are considered equal if they have the same name and date of birth.
     *
     * @param o The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    /**
     * Generates a hash code for this person.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    /**
     * Returns a string representation of this person.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}