package com.igorblazhko.booking.specification;

import com.igorblazhko.booking.entity.IgorBlazhkoRoomEntity;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class IgorBlazhkoRoomSpecification {

    private IgorBlazhkoRoomSpecification() {
    }

    public static Specification<IgorBlazhkoRoomEntity> filter(String search,
                                                              String city,
                                                              Boolean available,
                                                              Integer capacity,
                                                              BigDecimal minPrice,
                                                              BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (StringUtils.isNotBlank(search)) {
                String pattern = "%" + search.toLowerCase() + "%";
                predicates.getExpressions().add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("roomType")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("roomNumber")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("property").get("name")), pattern)
                ));
            }

            if (StringUtils.isNotBlank(city)) {
                predicates.getExpressions().add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("property").get("city")), city.toLowerCase()
                ));
            }

            if (available != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("available"), available));
            }

            if (capacity != null) {
                predicates.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity));
            }

            if (minPrice != null) {
                predicates.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerNight"), minPrice));
            }

            if (maxPrice != null) {
                predicates.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerNight"), maxPrice));
            }

            return predicates;
        };
    }
}