package com.hust.itss.repositories.route;

import com.hust.itss.models.route.RouteDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface RouteRepositoryCustom {
    public Page<RouteDetail> findRouteDetails(Pageable pageable);
}
