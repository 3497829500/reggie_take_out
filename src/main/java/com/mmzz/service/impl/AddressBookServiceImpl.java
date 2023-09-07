package com.mmzz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.AddressBook;
import com.mmzz.mapper.AddressBookMapper;
import com.mmzz.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
