package cashiersystem.entity;

public enum Role {
    ADMIN, MERCHANDISER, MANAGER, CASHIER;

    public static Role getRoleById(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return MERCHANDISER;
            case 3:
                return MANAGER;
            case 4:
                return CASHIER;
            default:
                throw new IllegalArgumentException("Can't get role for id: " + id);
        }
    }

    public int getId() {
        return this.ordinal() + 1;
    }
}
