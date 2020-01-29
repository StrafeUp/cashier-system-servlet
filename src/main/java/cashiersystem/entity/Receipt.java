package cashiersystem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Receipt {
    private final long id;
    private final Status status;
    private final long userId;
    private final List<Item> items;

    public Receipt(Builder builder) {
        this.id = builder.id;
        this.status = builder.status;
        this.userId = builder.userId;
        this.items = new ArrayList<>();
    }


    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public long getUserId() {
        return userId;
    }

    public List<Item> getItems() {
        return items;
    }

    private void addItem(Item item) {
        items.add(item);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof Receipt)) {return false;}
        Receipt receipt = (Receipt) o;
        return id == receipt.id &&
                userId == receipt.userId &&
                status == receipt.status &&
                Objects.equals(items, receipt.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, userId, items);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", status=" + status +
                ", userId=" + userId +
                ", items=" + items +
                '}';
    }

    public static class Builder {
        private long id;
        private Status status;
        private long userId;
        private List<Item> items;

        private Builder() {

        }

        public Receipt build() {
            return new Receipt(this);
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }
    }
}
