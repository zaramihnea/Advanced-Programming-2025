import java.util.Date;
import java.util.Objects;

/**
 * Represents a student in the system.
 * A student can be assigned to at most one project and has a list of acceptable projects.
 */
public class Student extends Person {
    private String id;
    private Project[] acceptableProjects;
    private Project assignedProject;
    private int projectCount;

    /**
     * Default constructor for Student.
     */
    public Student() {
        this.acceptableProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Constructor for Student with id and name.
     *
     * @param id   The student's ID
     * @param name The student's name
     */
    public Student(String id, String name) {
        super(name, null);
        this.id = id;
        this.acceptableProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Constructor for Student with all properties.
     *
     * @param id               The student's ID
     * @param name             The student's name
     * @param dateOfBirth      The student's date of birth
     * @param acceptableProjects The student's acceptable projects
     */
    public Student(String id, String name, Date dateOfBirth) {
        super(name, dateOfBirth);
        this.id = id;
        this.acceptableProjects = new Project[10]; // Default size
        this.projectCount = 0;
    }

    /**
     * Gets the student's ID.
     *
     * @return The student's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the student's ID.
     *
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the student's acceptable projects.
     *
     * @return Array of acceptable projects
     */
    public Project[] getAcceptableProjects() {
        Project[] result = new Project[projectCount];
        System.arraycopy(acceptableProjects, 0, result, 0, projectCount);
        return result;
    }

    /**
     * Sets the student's acceptable projects.
     *
     * @param acceptableProjects The projects to set
     */
    public void setAcceptableProjects(Project[] acceptableProjects) {
        this.acceptableProjects = acceptableProjects;
        this.projectCount = acceptableProjects.length;
    }

    /**
     * Gets the student's assigned project.
     *
     * @return The assigned project
     */
    public Project getAssignedProject() {
        return assignedProject;
    }

    /**
     * Sets the student's assigned project.
     *
     * @param assignedProject The project to assign
     */
    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }

    /**
     * Adds an acceptable project for this student.
     *
     * @param project The project to add
     */
    public void addAcceptableProject(Project project) {
        if (!containsProject(project) && projectCount < acceptableProjects.length) {
            acceptableProjects[projectCount++] = project;
        } else if (projectCount >= acceptableProjects.length) {
            // Resize array if needed
            Project[] newArray = new Project[acceptableProjects.length * 2];
            System.arraycopy(acceptableProjects, 0, newArray, 0, acceptableProjects.length);
            acceptableProjects = newArray;
            acceptableProjects[projectCount++] = project;
        }
    }

    /**
     * Checks if the project is already in the acceptable projects list.
     *
     * @param project The project to check
     * @return true if the project is already in the list, false otherwise
     */
    private boolean containsProject(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (acceptableProjects[i].equals(project)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an acceptable project for this student.
     *
     * @param project The project to remove
     */
    public void removeAcceptableProject(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (acceptableProjects[i].equals(project)) {
                // Remove by shifting elements
                System.arraycopy(acceptableProjects, i + 1, acceptableProjects, i, projectCount - i - 1);
                acceptableProjects[--projectCount] = null;
                break;
            }
        }
    }

    /**
     * Checks if this student is equal to another object.
     * Two students are considered equal if they have the same ID.
     *
     * @param o The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    /**
     * Generates a hash code for this student.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    /**
     * Returns a string representation of this student.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{id='").append(id).append('\'');
        sb.append(", name='").append(getName()).append('\'');

        sb.append(", acceptableProjects=[");
        for (int i = 0; i < projectCount; i++) {
            sb.append(acceptableProjects[i].getId());
            if (i < projectCount - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        if (assignedProject != null) {
            sb.append(", assignedProject=").append(assignedProject.getId());
        } else {
            sb.append(", assignedProject=null");
        }

        sb.append('}');
        return sb.toString();
    }
}