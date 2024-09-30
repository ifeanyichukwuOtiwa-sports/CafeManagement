package iwo.wintech.cafemanagement.persistence.api.category;

import iwo.wintech.cafemanagement.entity.category.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategory();
}
