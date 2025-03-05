import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private List<Project> acceptableProjects;
    private Project assignedProject;

    public Student() {
        this.acceptableProjects = new ArrayList<>();
    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.acceptableProjects = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getAcceptableProjects() {
        return acceptableProjects;
    }

    public void setAcceptableProjects(List<Project> acceptableProjects) {
        this.acceptableProjects = acceptableProjects;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }

    public void addAcceptableProject(Project project) {
        if (!acceptableProjects.contains(project)) {
            acceptableProjects.add(project);
        }
    }

    public void removeAcceptableProject(Project project) {
        acceptableProjects.remove(project);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", acceptableProjects=" + acceptableProjects +
                ", assignedProject=" + assignedProject +
                '}';
    }
}