package hovanvydut.apiblog.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

public class SortAndPaginationUtil {

    public static Pageable processSortAndPagination(int page, int size, String[] sort) {
        Sort sortObj = SortAndPaginationUtil.processSort(sort);

        Pageable pageable;
        if (sortObj != null)
            pageable = PageRequest.of(page - 1, size, sortObj);
        else
            pageable = PageRequest.of(page - 1, size);

        return pageable;
    }

    public static Sort processSort(String[] sorts) {
        if (sorts == null || sorts.length == 0) return null;

        List<Order> orders = new ArrayList<>();

        if (sorts[0].contains(",")) {
            // will sorts equals or more than 2 fields
            // sortOrder="field, direction"
            for (String sort : sorts) {
                String[] _sort = sort.replace("\\s", "").split(",");
                Direction direction = getSortDirection(_sort[1]);
                orders.add(new Order(direction, _sort[0]));
            }
        } else {
            // sorts=[field, direction]
            String field = sorts[0].replace("\\s", "");
            String direction = sorts[1].replace("\\s", "");

            orders.add(new Order(getSortDirection(direction), field));
        }

        return Sort.by(orders);
    }

    private static Direction getSortDirection(String direction) {
        if ("desc".equals(direction)) {
            return Direction.DESC;
        }
        return Direction.ASC;
    }
}
