import java.util.Objects;

/**
 * Represents a project in the system.
 * A project can be of two types: theoretical or practical.
 */
public class Project {
    private String id;
    private String name;
    private ProjectType type;
    private Teacher proposedBy;

    /**
     * Default constructor for Project.
     */
    public Project() {
    }

    /**
     * Constructor for Project with id, name, and type.
     *
     * @param id   The project's ID
     * @param name The project's name
     * @param type The project's type
     */
    public Project(String id, String name, ProjectType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Constructor for Project with all properties.
     *
     * @param id        The project's ID
     * @param name      The project's name
     * @param type      The project's type
     * @param proposedBy The teacher who proposed this project
     */
    public Project(String id, String name, ProjectType type, Teacher proposedBy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.proposedBy = proposedBy;
    }

    /**
     * Gets the project's ID.
     *
     * @return The project's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the project's ID.
     *
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the project's name.
     *
     * @return The project's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the project's name.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the project's type.
     *
     * @return The project's type
     */
    public ProjectType getType() {
        return type;
    }

    /**
     * Sets the project's type.
     *
     * @param type The type to set
     */
    public void setType(ProjectType type) {
        this.type = type;
    }

    /**
     * Gets the teacher who proposed this project.
     *
     * @return The teacher
     */
    public Teacher getProposedBy() {
        return proposedBy;
    }

    /**
     * Sets the teacher who proposed this project.
     *
     * @param proposedBy The teacher to set
     */
    public void setProposedBy(Teacher proposedBy) {
        this.proposedBy = proposedBy;
    }

    /**
     * Checks if this project is equal to another object.
     * Two projects are considered equal if they have the same ID.
     *
     * @param o The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    /**
     * Generates a hash code for this project.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this project.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project{id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);

        if (proposedBy != null) {
            sb.append(", proposedBy='").append(proposedBy.getName()).append('\'');
        } else {
            sb.append(", proposedBy=null");
        }

        sb.append('}');
        return sb.toString();
    }
}