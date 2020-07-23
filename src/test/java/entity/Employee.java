package entity;

public class Employee {
    private final String email;
    private final String password;

    public static class EmployeeBuilder {
        private final String email;
        private final String password;

        public EmployeeBuilder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    private Employee(EmployeeBuilder employeeBuilder) {
        this.email = employeeBuilder.email;
        this.password = employeeBuilder.password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
