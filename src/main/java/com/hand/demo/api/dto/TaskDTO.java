package com.hand.demo.api.dto;

import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hzero.export.annotation.ExcelSheet;

import java.util.List;

@Getter
@Setter
@ExcelSheet(en = "Task info")
@Accessors(chain = true)
public class TaskDTO extends Task {
    private List<Long> empIdList;
}
