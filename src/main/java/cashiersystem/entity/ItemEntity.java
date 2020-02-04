package cashiersystem.entity;

import java.util.Objects;

public class ItemEntity {
    private final Long id;
    private final String name;
    private final double weight;
    private final int quantity;

    public ItemEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.weight = builder.weight;
        this.quantity = builder.quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ItemEntity itemEntity = (ItemEntity) o;
        return Double.compare(itemEntity.weight, weight) == 0 &&
                quantity == itemEntity.quantity &&
                Objects.equals(id, itemEntity.id) &&
                Objects.equals(name, itemEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, quantity);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private double weight;
        private int quantity;

        private Builder() {
        }

        public ItemEntity build() {
            return new ItemEntity(this);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
    }
}
