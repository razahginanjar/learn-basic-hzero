package com.hand.demo.api.dto;

import com.hand.demo.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.List;

@Getter
@Setter
@ExcelSheet(en = "User Info", zh = "User Info")
public class UserDTO extends User {
    @ExcelColumn(promptCode = "children", promptKey = "children", child = true)
    private List<TaskDTO> tasks;
}
