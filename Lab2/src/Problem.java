/**
 * Represents the Student Project Allocation Problem.
 * Contains the lists of students, teachers, and projects to be matched.
 */
public class Problem {
    private Student[] students;
    private Teacher[] teachers;
    private Project[] projects;
    private int studentCount;
    private int teacherCount;
    private int projectCount;

    /**
     * Default constructor for Problem.
     * Initializes empty arrays with default sizes.
     */
    public Problem() {
        this.students = new Student[20];
        this.teachers = new Teacher[10];
        this.projects = new Project[30];
        this.studentCount = 0;
        this.teacherCount = 0;
        this.projectCount = 0;
    }

    /**
     * Constructor for Problem with specific array sizes.
     *
     * @param studentCapacity The capacity for students
     * @param teacherCapacity The capacity for teachers
     * @param projectCapacity The capacity for projects
     */
    public Problem(int studentCapacity, int teacherCapacity, int projectCapacity) {
        this.students = new Student[studentCapacity];
        this.teachers = new Teacher[teacherCapacity];
        this.projects = new Project[projectCapacity];
        this.studentCount = 0;
        this.teacherCount = 0;
        this.projectCount = 0;
    }

    /**
     * Adds a student to the problem if not already added.
     *
     * @param student The student to add
     * @return true if the student was added, false otherwise
     */
    public boolean addStudent(Student student) {
        // Check if student already exists
        for (int i = 0; i < studentCount; i++) {
            if (students[i].equals(student)) {
                return false;
            }
        }

        // Resize array if needed
        if (studentCount >= students.length) {
            Student[] newArray = new Student[students.length * 2];
            System.arraycopy(students, 0, newArray, 0, students.length);
            students = newArray;
        }

        students[studentCount++] = student;
        return true;
    }

    /**
     * Adds a teacher to the problem if not already added.
     *
     * @param teacher The teacher to add
     * @return true if the teacher was added, false otherwise
     */
    public boolean addTeacher(Teacher teacher) {
        // Check if teacher already exists
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].equals(teacher)) {
                return false;
            }
        }

        // Resize array if needed
        if (teacherCount >= teachers.length) {
            Teacher[] newArray = new Teacher[teachers.length * 2];
            System.arraycopy(teachers, 0, newArray, 0, teachers.length);
            teachers = newArray;
        }

        teachers[teacherCount++] = teacher;
        return true;
    }

    /**
     * Adds a project to the problem if not already added.
     *
     * @param project The project to add
     * @return true if the project was added, false otherwise
     */
    public boolean addProject(Project project) {
        // Check if project already exists
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].equals(project)) {
                return false;
            }
        }

        // Resize array if needed
        if (projectCount >= projects.length) {
            Project[] newArray = new Project[projects.length * 2];
            System.arraycopy(projects, 0, newArray, 0, projects.length);
            projects = newArray;
        }

        projects[projectCount++] = project;
        return true;
    }

    /**
     * Gets all students in the problem.
     *
     * @return Array of students
     */
    public Student[] getStudents() {
        Student[] result = new Student[studentCount];
        System.arraycopy(students, 0, result, 0, studentCount);
        return result;
    }

    /**
     * Gets all teachers in the problem.
     *
     * @return Array of teachers
     */
    public Teacher[] getTeachers() {
        Teacher[] result = new Teacher[teacherCount];
        System.arraycopy(teachers, 0, result, 0, teacherCount);
        return result;
    }

    /**
     * Gets all projects in the problem.
     *
     * @return Array of projects
     */
    public Project[] getProjects() {
        Project[] result = new Project[projectCount];
        System.arraycopy(projects, 0, result, 0, projectCount);
        return result;
    }

    /**
     * Gets all persons (students and teachers) involved in the problem.
     *
     * @return Array of all persons
     */
    public Person[] getAllPersons() {
        Person[] result = new Person[studentCount + teacherCount];

        // Copy students
        for (int i = 0; i < studentCount; i++) {
            result[i] = students[i];
        }

        // Copy teachers
        for (int i = 0; i < teacherCount; i++) {
            result[studentCount + i] = teachers[i];
        }

        return result;
    }

    /**
     * Implements a simple greedy algorithm for allocating projects to students.
     * For each student, it assigns the first acceptable project that is not yet assigned.
     *
     * @return A Solution object containing the allocation
     */
    public Solution solveGreedy() {
        Solution solution = new Solution(studentCount);

        // Create an array to track assigned projects
        boolean[] projectAssigned = new boolean[projectCount];

        // For each student
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            Project[] acceptableProjects = student.getAcceptableProjects();

            // Try to find an unassigned acceptable project
            boolean assigned = false;
            for (Project acceptableProject : acceptableProjects) {
                // Find the project in our projects array
                for (int k = 0; k < projectCount; k++) {
                    if (projects[k].equals(acceptableProject) && !projectAssigned[k]) {
                        // Assign this project to the student
                        student.setAssignedProject(projects[k]);
                        projectAssigned[k] = true;
                        solution.addAssignment(student, projects[k]);
                        assigned = true;
                        break;
                    }
                }

                if (assigned) {
                    break;
                }
            }
        }

        return solution;
    }

    /**
     * Returns a string representation of this problem.
     *
     * @return A string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problem{\n");

        sb.append("  Students (").append(studentCount).append("):\n");
        for (int i = 0; i < studentCount; i++) {
            sb.append("    ").append(students[i]).append("\n");
        }

        sb.append("  Teachers (").append(teacherCount).append("):\n");
        for (int i = 0; i < teacherCount; i++) {
            sb.append("    ").append(teachers[i]).append("\n");
        }

        sb.append("  Projects (").append(projectCount).append("):\n");
        for (int i = 0; i < projectCount; i++) {
            sb.append("    ").append(projects[i]).append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
}