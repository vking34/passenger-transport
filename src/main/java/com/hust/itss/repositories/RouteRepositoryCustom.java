package com.hust.itss.repositories;

import com.hust.itss.models.routes.RouteDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteRepositoryCustom {
    public Page<RouteDetail> findRouteDetails(Pageable pageable);
}
