package com.mmzz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmzz.domain.Category;

public interface CategoryService extends IService<Category> {
    /**
     * 通过id进行删除，，
     * 校验是否存在
     * @param id
     */
    public void remove(Long id);

}
