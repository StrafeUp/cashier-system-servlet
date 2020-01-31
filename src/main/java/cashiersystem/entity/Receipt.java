package cashiersystem.entity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Receipt {
    private final Long id;
    private final Long receiptId;
    private final Status status;
    private final User user;
    private final List<Item> items;

    public Receipt(Builder builder) {
        this.id = builder.id;
        this.receiptId = builder.receiptId;
        this.status = builder.status;
        this.user = builder.user;
        this.items = builder.items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) o;
        return Objects.equals(id, receipt.id) &&
                Objects.equals(receiptId, receipt.receiptId) &&
                status == receipt.status &&
                Objects.equals(user, receipt.user) &&
                Objects.equals(items, receipt.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiptId, status, user, items);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + user +
                ", items=" + items +
                '}';
    }

    public static class Builder {
        private Long id;
        private Long receiptId;
        private Status status;
        private User user;
        private List<Item> items;

        private Builder() {

        }

        public Receipt build() {
            if (items == null) {
                items = Collections.emptyList();
            }
            return new Receipt(this);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }
        public Builder withReceiptId(Long receiptId){
            this.receiptId = id;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }
    }
}
