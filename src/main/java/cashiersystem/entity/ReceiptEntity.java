package cashiersystem.entity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReceiptEntity {
    private final Long id;
    private final Long receiptId;
    private final Status status;
    private final UserEntity userEntity;
    private final List<ItemEntity> itemEntities;

    public ReceiptEntity(Builder builder) {
        this.id = builder.id;
        this.receiptId = builder.receiptId;
        this.status = builder.status;
        this.userEntity = builder.userEntity;
        this.itemEntities = builder.itemEntities;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public List<ItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void addItem(ItemEntity itemEntity) {
        itemEntities.add(itemEntity);
    }

    public void removeItem(ItemEntity itemEntity) {
        itemEntities.remove(itemEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ReceiptEntity receiptEntity = (ReceiptEntity) o;
        return Objects.equals(id, receiptEntity.id) &&
                Objects.equals(receiptId, receiptEntity.receiptId) &&
                status == receiptEntity.status &&
                Objects.equals(userEntity, receiptEntity.userEntity) &&
                Objects.equals(itemEntities, receiptEntity.itemEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiptId, status, userEntity, itemEntities);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + userEntity +
                ", items=" + itemEntities +
                '}';
    }

    public static class Builder {
        private Long id;
        private Long receiptId;
        private Status status;
        private UserEntity userEntity;
        private List<ItemEntity> itemEntities;

        private Builder() {

        }

        public ReceiptEntity build() {
            if (itemEntities == null) {
                itemEntities = Collections.emptyList();
            }
            return new ReceiptEntity(this);
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

        public Builder withUser(UserEntity userEntity) {
            this.userEntity = userEntity;
            return this;
        }

        public Builder withItems(List<ItemEntity> itemEntities) {
            this.itemEntities = itemEntities;
            return this;
        }
    }
}
