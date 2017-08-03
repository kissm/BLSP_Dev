package com.lpcode.modules.blsp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.core.base.page.Page;
import com.framework.core.result.RequestDTOPage;
import com.lpcode.modules.blsp.vo.CertLpcodeVO;
import com.lpcode.modules.blsp.vo.PrjTaskDependencyListVO;
import com.lpcode.modules.blsp.vo.PrjTaskForOffLineFinishVO;
import com.lpcode.modules.blsp.vo.PrjTaskOvertimeVO;
import com.lpcode.modules.blsp.vo.PrjTaskPauseDueVO;
import com.lpcode.modules.blsp.vo.PrjTaskPauseTimesVO;
import com.lpcode.modules.blsp.vo.PrjTaskTodoListVO;

public interface PrjTaskAdapterMapper {
	
	List<PrjTaskTodoListVO> findList(RequestDTOPage<PrjTaskTodoListVO> para);
	
	Page<PrjTaskTodoListVO> pagedSelectByEntity(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	Page<PrjTaskTodoListVO> pagedSelectByEntityForMyTask(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	Page<PrjTaskTodoListVO> pagedSelectByEntityForMyTaskFinish(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	Page<PrjTaskTodoListVO> pagedSelectByEntityForCert(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	Page<PrjTaskTodoListVO> pagedSelectForPrjCert(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	Page<PrjTaskTodoListVO> pagedSelectForRejectBackList(@Param("record") PrjTaskTodoListVO record, @Param("page") Page<PrjTaskTodoListVO> page);
	
	int countBy(RequestDTOPage<PrjTaskTodoListVO> para);
	
	List<PrjTaskPauseDueVO> findPauseDueList();
	
	List<PrjTaskOvertimeVO> findOvertimeList();
	
	List<PrjTaskDependencyListVO> findTaskDependencyList(@Param("prjId") Long prjId, @Param("taskId") Long taskId);

	CertLpcodeVO findCertLpcodeInfo(@Param("prjTaskInstId") Long prjTaskInstId, @Param("status") String status);
	
	List<PrjTaskForOffLineFinishVO> findTaskForOffLineFinish(@Param("prjInsId") Long prjInsId, @Param("stageInsId") Long stageInsId);

	List<PrjTaskForOffLineFinishVO> findTaskForUnPass(@Param("prjInsId") Long prjInsId, @Param("stageInsId") Long stageInsId);

	// 查询补齐材料暂停次数
	int countPauseTimes(@Param("prjId") Long prjId);
	
	// 查询补齐材料事项
	List<PrjTaskPauseTimesVO> findPauseForMater(@Param("prjId") Long prjId);

	//根据对象中的条件获取代办列表
	List<PrjTaskTodoListVO> selectBacklogListByEntity(@Param("record") PrjTaskTodoListVO vo);
}