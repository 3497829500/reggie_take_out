package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mmzz.common.BaseContext;
import com.mmzz.common.R;
import com.mmzz.domain.AddressBook;
import com.mmzz.service.AddressBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Resource
    private AddressBookService addressBookService;


    /**
     * 新增操作
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
    return R.success(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> lambdaUpdateWrapper=new LambdaUpdateWrapper<AddressBook>();
        lambdaUpdateWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lambdaUpdateWrapper.set(AddressBook::getIsDefault,0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(lambdaUpdateWrapper);

        //设置当前地址为默认地址
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id){
        AddressBook byId = addressBookService.getById(id);
        if (byId != null){
            return R.success(byId);
        }else {
            return R.error("未找到该地址");
        }
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper=new LambdaQueryWrapper<AddressBook>();
        lambdaQueryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lambdaQueryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(lambdaQueryWrapper);
        if (null == addressBook){
            return R.error("未找到默认地址");
        }else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     * @param addressBook
     * @return
     */
    @GetMapping("list")
    public R<List<AddressBook>> list(AddressBook addressBook){
    addressBook.setUserId(BaseContext.getCurrentId());

    LambdaQueryWrapper<AddressBook> lambdaQueryWrapper=new LambdaQueryWrapper<AddressBook>();
    lambdaQueryWrapper.eq(null!=addressBook.getUserId(),AddressBook::getUserId,addressBook.getUserId());
    lambdaQueryWrapper.orderByDesc(AddressBook::getUpdateTime);
//SQL:select * from address_book where user_id = ? order by update_time desc
    return R.success(addressBookService.list(lambdaQueryWrapper));
    }


}
