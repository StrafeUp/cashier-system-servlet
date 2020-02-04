package cashiersystem.dao.domain;

import cashiersystem.entity.Role;

public class User {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final Role role;

    public User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static User.Builder builder() {
        return new User.Builder();
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Role role;

        private Builder() {
        }

        public User build() {
            return new User(this);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }
    }
}
