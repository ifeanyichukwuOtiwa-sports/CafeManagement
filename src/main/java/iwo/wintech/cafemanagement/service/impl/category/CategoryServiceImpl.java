package iwo.wintech.cafemanagement.service.impl.category;

import iwo.wintech.cafemanagement.dto.AddCategoryRequest;
import iwo.wintech.cafemanagement.persistence.api.category.CategoryRepository;
import iwo.wintech.cafemanagement.service.api.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public String addNewCategory(final AddCategoryRequest req) {
        return "";
    }
}
