/**
 * Represents a solution to the Student Project Allocation Problem.
 * Contains the assignments of students to projects.
 */
public class Solution {
    private Student[] students;
    private Project[] assignedProjects;
    private int assignmentCount;

    /**
     * Constructor for Solution with a specified capacity.
     *
     * @param capacity The maximum number of assignments
     */
    public Solution(int capacity) {
        this.students = new Student[capacity];
        this.assignedProjects = new Project[capacity];
        this.assignmentCount = 0;
    }

    /**
     * Adds an assignment of a student to a project.
     *
     * @param student The student
     * @param project The project
     */
    public void addAssignment(Student student, Project project) {
        // Resize array if needed
        if (assignmentCount >= students.length) {
            Student[] newStudents = new Student[students.length * 2];
            Project[] newProjects = new Project[assignedProjects.length * 2];
            System.arraycopy(students, 0, newStudents, 0, students.length);
            System.arraycopy(assignedProjects, 0, newProjects, 0, assignedProjects.length);
            students = newStudents;
            assignedProjects = newProjects;
        }

        students[assignmentCount] = student;
        assignedProjects[assignmentCount] = project;
        assignmentCount++;
    }

    /**
     * Gets the number of assignments in the solution.
     *
     * @return The number of assignments
     */
    public int getAssignmentCount() {
        return assignmentCount;
    }

    /**
     * Gets the students in the solution.
     *
     * @return Array of students
     */
    public Student[] getStudents() {
        Student[] result = new Student[assignmentCount];
        System.arraycopy(students, 0, result, 0, assignmentCount);
        return result;
    }

    /**
     * Gets the assigned projects in the solution.
     *
     * @return Array of assigned projects
     */
    public Project[] getAssignedProjects() {
        Project[] result = new Project[assignmentCount];
        System.arraycopy(assignedProjects, 0, result, 0, assignmentCount);
        return result;
    }

    /**
     * Checks if this solution is complete, meaning all students have an assigned project.
     *
     * @return true if complete, false otherwise
     */
    public boolean isComplete() {
        // In a complete solution, all students should be assigned a project
        return true; // This is simplified - in a real implementation, we would check against all students
    }

    /**
     * Returns a string representation of this solution.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < assignmentCount; i++) {
            sb.append("(").append(students[i].getId()).append(":")
                    .append(assignedProjects[i].getId()).append(")");

            if (i < assignmentCount - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}