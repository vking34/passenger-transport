package com.hust.itss.utils.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestCreation {

    public static PageRequest getSimplePageRequest(Integer page, Integer pageSize){
        if(page == null)
            page = 0;
        if(pageSize == null)
            pageSize = 5;
        return PageRequest.of(page, pageSize);
    }

    public static PageRequest getPageRequest(Integer page, Integer pageSize, String sort, String direct, List fields){
        PageRequest pageRequest;
        if(page == null)
            page = 0;
        if(pageSize == null)
            pageSize = 5;

        if(fields.contains(sort))
        {
            if(direct.equals("DESC"))
                pageRequest = PageRequest.of(page , pageSize, Sort.by(sort).descending());
            else {
                pageRequest = PageRequest.of(page, pageSize, Sort.by(sort).ascending());
            }
        }
        else {
            pageRequest = PageRequest.of(page, pageSize);
        }

        return pageRequest;
    }

    public static PageRequest getBasicPageRequest(Integer page, Integer pageSize, String sort, String direct)
    {
        PageRequest pageRequest;
        if(page == null)
            page = 0;
        if(pageSize == null)
            pageSize = 5;
        if (direct != null)
        {
            if( direct.equals("DESC"))
                pageRequest = PageRequest.of(page, pageSize, Sort.by(sort).descending());
            else {
                pageRequest = PageRequest.of(page, pageSize, Sort.by(sort).ascending());
            }
        }
        else {
            pageRequest = PageRequest.of(page, pageSize);
        }
        return pageRequest;
    }
}
