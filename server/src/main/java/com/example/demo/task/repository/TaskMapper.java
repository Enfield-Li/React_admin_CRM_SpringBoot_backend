package com.example.demo.task.repository;

import com.example.demo.task.entity.Task;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TaskMapper {
  List<Task> getAllTasks(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("contact_id") Long contact_id
  );

  @Select("SELECT count(*) FROM task")
  String getTaskCount();
}
