package cashiersystem.service.mapper;

import cashiersystem.dao.domain.Receipt;
import cashiersystem.entity.ReceiptEntity;

public class ReceiptMapper implements Mapper<ReceiptEntity, Receipt> {
    @Override
    public ReceiptEntity mapDomainToEntity(Receipt domain) {
        return ReceiptEntity.builder()
                .withId(domain.getId())
                .withUser(domain.getUserEntity())
                .withStatus(domain.getStatus())
                .withReceiptId(domain.getReceiptId())
                .withItems(domain.getItemEntities())
                .build();
    }

    @Override
    public Receipt mapEntityToDomain(ReceiptEntity entity) {
        return Receipt.builder()
                .withId(entity.getId())
                .withUser(entity.getUserEntity())
                .withStatus(entity.getStatus())
                .withReceiptId(entity.getReceiptId())
                .withItems(entity.getItemEntities())
                .build();
    }
}
