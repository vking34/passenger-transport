package com.hust.itss.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestCreation {

    public static PageRequest getPageRequest(Integer page, Integer pageSize, String sort, String direct, List fields){
        PageRequest pageRequest;
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 10;

        if(fields.contains(sort))
        {
            if(direct.equals("DESC"))
                pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(sort).descending());
            else {
                pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(sort).ascending());
            }
        }
        else {
            pageRequest = PageRequest.of(page- 1, pageSize);
        }

        return pageRequest;
    }

    public static PageRequest getBasicPageRequest(Integer page, Integer pageSize, String sort, String direct)
    {
        PageRequest pageRequest;
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 10;
        if (direct != null)
        {
            if( direct.equals("DESC"))
                pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(sort).descending());
            else {
                pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(sort).ascending());
            }
        }
        else {
            pageRequest = PageRequest.of(page-1, pageSize);
        }
        return pageRequest;
    }
}
