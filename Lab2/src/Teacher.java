import java.util.Date;
import java.util.Objects;

/**
 * Represents a teacher in the system.
 * A teacher can propose multiple projects.
 */
public class Teacher extends Person {
    private String id;
    private Project[] proposedProjects;
    private int projectCount;

    /**
     * Default constructor for Teacher.
     */
    public Teacher() {
        this.proposedProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Constructor for Teacher with id and name.
     *
     * @param id   The teacher's ID
     * @param name The teacher's name
     */
    public Teacher(String id, String name) {
        super(name, null);
        this.id = id;
        this.proposedProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Constructor for Teacher with all properties.
     *
     * @param id           The teacher's ID
     * @param name         The teacher's name
     * @param dateOfBirth  The teacher's date of birth
     */
    public Teacher(String id, String name, Date dateOfBirth) {
        super(name, dateOfBirth);
        this.id = id;
        this.proposedProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Gets the teacher's ID.
     *
     * @return The teacher's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the teacher's ID.
     *
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the teacher's proposed projects.
     *
     * @return Array of proposed projects
     */
    public Project[] getProposedProjects() {
        Project[] result = new Project[projectCount];
        System.arraycopy(proposedProjects, 0, result, 0, projectCount);
        return result;
    }

    /**
     * Adds a proposed project for this teacher.
     *
     * @param project The project to add
     */
    public void addProposedProject(Project project) {
        if (!containsProject(project) && projectCount < proposedProjects.length) {
            proposedProjects[projectCount++] = project;
            project.setProposedBy(this);
        } else if (projectCount >= proposedProjects.length) {
            // Resize array if needed
            Project[] newArray = new Project[proposedProjects.length * 2];
            System.arraycopy(proposedProjects, 0, newArray, 0, proposedProjects.length);
            proposedProjects = newArray;
            proposedProjects[projectCount++] = project;
            project.setProposedBy(this);
        }
    }

    /**
     * Checks if the project is already in the proposed projects list.
     *
     * @param project The project to check
     * @return true if the project is already in the list, false otherwise
     */
    private boolean containsProject(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (proposedProjects[i].equals(project)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a proposed project for this teacher.
     *
     * @param project The project to remove
     */
    public void removeProposedProject(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (proposedProjects[i].equals(project)) {
                // Remove by shifting elements
                System.arraycopy(proposedProjects, i + 1, proposedProjects, i, projectCount - i - 1);
                proposedProjects[--projectCount] = null;
                break;
            }
        }
    }

    /**
     * Checks if this teacher is equal to another object.
     * Two teachers are considered equal if they have the same ID.
     *
     * @param o The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }

    /**
     * Generates a hash code for this teacher.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    /**
     * Returns a string representation of this teacher.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Teacher{id='").append(id).append('\'');
        sb.append(", name='").append(getName()).append('\'');

        sb.append(", proposedProjects=[");
        for (int i = 0; i < projectCount; i++) {
            sb.append(proposedProjects[i].getId());
            if (i < projectCount - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        sb.append('}');
        return sb.toString();
    }
}