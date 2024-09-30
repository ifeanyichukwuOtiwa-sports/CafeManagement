package iwo.wintech.cafemanagement.dto;

import java.util.List;

public record ListResponse<T>(
        List<T> items,
        int count
) {
    public static <T> ListResponse<T> of(List<T> items) {
        return new ListResponse<>(items, items.size());
    }
}
