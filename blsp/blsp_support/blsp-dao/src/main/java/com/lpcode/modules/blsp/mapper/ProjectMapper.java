package com.lpcode.modules.blsp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lpcode.modules.blsp.vo.ProjectVo;

public interface ProjectMapper {
	public List<ProjectVo> selectList(@Param("from")Date from,@Param("to")Date to, @Param("start")int start, @Param("pageSize")int pageSize,@Param("type")String type);
	public List<ProjectVo> select(@Param("from")Date from,@Param("to")Date to, @Param("start")int start, @Param("pageSize")int pageSize,@Param("type")String type);
	public List<ProjectVo> selectProcessPrj(@Param("from")Date from,@Param("to")Date to, @Param("start")int start, @Param("pageSize")int pageSize,@Param("type")String type);
	public List<ProjectVo> selectAllproject(@Param("from")Date from,@Param("to")Date to, @Param("start")int start, @Param("pageSize")int pageSize,@Param("type")String type);
	public List<ProjectVo> selectAllinitPrj(@Param("from")Date from,@Param("to")Date to, @Param("start")int start, @Param("pageSize")int pageSize,@Param("type")String type);
	public Integer selectCount(@Param("from")Date from,@Param("to")Date to,@Param("type")String type);
	public Integer selectProcessCount(@Param("type")String type);
}
