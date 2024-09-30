package iwo.wintech.cafemanagement.rest.category;

import iwo.wintech.cafemanagement.dto.AddCategoryRequest;
import iwo.wintech.cafemanagement.rest.CafeMgmtUriMappings;
import iwo.wintech.cafemanagement.service.api.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = CafeMgmtUriMappings.CATEGORY_URI)
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("add")
    public String addCategory(final AddCategoryRequest req) {
        return categoryService.addNewCategory(req);
    }
}
