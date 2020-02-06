package cashiersystem.service.impl;

import cashiersystem.dao.ItemCrudDao;
import cashiersystem.dao.domain.Item;
import cashiersystem.dao.domain.Page;
import cashiersystem.entity.ItemEntity;
import cashiersystem.service.ItemService;
import cashiersystem.service.mapper.ItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    private final ItemCrudDao itemPageableCrudDao;
    private final ItemMapper itemMapper;

    //TODO validation
    public ItemServiceImpl(ItemCrudDao itemPageableCrudDao, ItemMapper itemMapper) {
        this.itemPageableCrudDao = itemPageableCrudDao;
        this.itemMapper = itemMapper;
    }

    @Override
    public void addItem(ItemEntity itemEntity) {
        Optional<ItemEntity> itemFromDb = itemPageableCrudDao.findByName(itemEntity.getName());
        itemFromDb.ifPresent(value -> itemPageableCrudDao.update(ItemEntity.builder()
                .withQuantity(value.getQuantity() + itemEntity.getQuantity())
                .withId(itemEntity.getId())
                .withName(itemEntity.getName())
                .withWeight(itemEntity.getWeight())
                .build()));
        itemPageableCrudDao.save(itemEntity);
    }

    @Override
    public Item getItemById(Integer id) {
        return itemMapper.mapEntityToDomain(itemPageableCrudDao.findById(id).get());
    }

    @Override
    public Item getItemByName(String name) {
        return itemMapper.mapEntityToDomain(itemPageableCrudDao.findByName(name).get());

    }

    @Override
    public void updateItem(ItemEntity itemEntity) {
        itemPageableCrudDao.update(itemEntity);
    }

    @Override
    public void removeItemById(Integer id) {
        itemPageableCrudDao.deleteById(id);
    }

    @Override
    public int count() {
        return (int) itemPageableCrudDao.count();
    }

    @Override
    public List<Item> findAll(Page page) {
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
        return itemPageableCrudDao.findAll(new Page(pageNumber, page.getItemsPerPage())).stream()
                .map(itemMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

}
