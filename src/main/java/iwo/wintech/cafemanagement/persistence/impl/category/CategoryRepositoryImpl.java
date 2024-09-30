package iwo.wintech.cafemanagement.persistence.impl.category;

import iwo.wintech.cafemanagement.entity.category.Category;
import iwo.wintech.cafemanagement.persistence.api.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<Category> getAllCategory() {
        return List.of();
    }
}
