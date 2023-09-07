package com.mmzz.dto;

import com.mmzz.domain.Setmeal;
import com.mmzz.domain.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
