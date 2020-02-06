package cashiersystem.service.impl;

import cashiersystem.dao.ReceiptCrudDao;
import cashiersystem.dao.domain.Item;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.Receipt;
import cashiersystem.entity.ReceiptEntity;
import cashiersystem.service.ReceiptService;
import cashiersystem.service.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptServiceImpl implements ReceiptService {
    private ReceiptCrudDao receiptCrudDao;
    private Mapper<ReceiptEntity, Receipt> receiptMapper;

    public ReceiptServiceImpl(ReceiptCrudDao receiptCrudDao, Mapper<ReceiptEntity, Receipt> receiptMapper) {
        this.receiptCrudDao = receiptCrudDao;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public void saveReceipt(Receipt receipt) {
        receiptCrudDao.save(receiptMapper.mapDomainToEntity(receipt));
    }

    @Override
    public Receipt getByReceiptId(Long receiptId) {
        return receiptMapper.mapEntityToDomain(receiptCrudDao.findByReceiptId(receiptId));
    }

    @Override
    public void removeItemFromReceipt(Long receiptId, Item item) {
        getByReceiptId(receiptId).getItemEntities().remove(item);
    }

    @Override
    public List<Receipt> findAll(Page page) {
        int maxPageNumber = (int) Math.ceil(count() * 1.0 / page.getItemsPerPage());
        int pageNumber = page.getPageNumber();

        if (maxPageNumber <= 0) {
            maxPageNumber = 1;
        }
        if (pageNumber <= 0) {
            pageNumber = 1;

        } else if (pageNumber >= maxPageNumber) {
            pageNumber = maxPageNumber;
        }
        return receiptCrudDao.findAll(new Page(pageNumber, page.getItemsPerPage())).stream()
                .map(receiptMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) receiptCrudDao.count();
    }
}
