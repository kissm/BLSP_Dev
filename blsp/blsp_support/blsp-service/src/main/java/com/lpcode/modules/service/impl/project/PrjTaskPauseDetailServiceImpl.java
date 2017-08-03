/**
 *
 */
package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lpcode.modules.service.report.ReportPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.result.CommonResult;
import com.framework.core.result.ListResult;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjTaskPauseDetail;
import com.lpcode.modules.blsp.entity.PrjTaskPauseDetailExample;
import com.lpcode.modules.blsp.mapper.PrjTaskPauseDetailMapper;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.project.dto.PrjTaskPauseDTO;
import com.lpcode.modules.service.project.inf.PrjTaskPauseDetailService;

/**
 * 项目事项暂停表ServiceImpl
 *
 * @author wangyazhou
 *
 */
@Service
@Transactional(readOnly = true)
public class PrjTaskPauseDetailServiceImpl implements PrjTaskPauseDetailService {

	@Autowired
	private PrjTaskPauseDetailMapper mapper;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private ReportPushService reportPushService;

	@Override
	@Transactional(readOnly = false)
	public CommonResult savePause(PrjTaskPauseDTO dto) {

		PrjTaskPauseDetail pauseEntity = new PrjTaskPauseDetail();
		BeanCopy.copyProperties(dto, pauseEntity);

		pauseEntity.setCreator(dto.getCreator());
		Date currentDate = new Date();
		pauseEntity.setPauseStartTime(currentDate);// 暂停开始时间

		pauseEntity.setPauseOvertime(dto.getPauseDate());// 暂停应结束时间

		if ("1".equals(dto.getSendMessage())) {
			pauseEntity.setIsSendMessage("1");
			pauseEntity.setProvideEndTime(dto.getProvideEndDate());
		}

		mapper.insert(pauseEntity);
		//TODO 特别程序申请的上报
		reportPushService.sbTbcxsqDataMethod(pauseEntity);
		return new CommonResult();
	}

	/**
	 * 恢复
	 *
	 * @param dto
	 * @return
	 */

	@Override
	@Transactional(readOnly = false)
	public Result<Integer> resume(PrjTaskPauseDTO dto) {

		// 跟新实际结束时间为空的那条记录
		PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
		PrjTaskPauseDetailExample.Criteria criteria = example.createCriteria();
		criteria.andPrjTaskInstIdEqualTo(dto.getPrjTaskInstId());
		criteria.andPauseEndTimeIsNull();
		List<PrjTaskPauseDetail> list = mapper.selectByExample(example);
		int pauseDuration = 0;
		if (list != null && list.size() > 0) {
			PrjTaskPauseDetail pauseEntity = list.get(0);

			Date endDate = new Date();
			pauseEntity.setPauseEndTime(endDate);

			// 计算暂停耗时
			pauseDuration = dimHolidayService.calculatePausePeriod(pauseEntity.getPauseStartTime(), endDate,
					dto.getTimeType());
			pauseEntity.setPauseDuration(pauseDuration);

			mapper.updateByPrimaryKeySelective(pauseEntity);
			dto.setDuration(pauseEntity.getDuration());
			dto.setPauseDate(pauseEntity.getPauseStartTime());
			// 特别程序申请结果的上报
			reportPushService.sbTbcxsqjgDataMethod(pauseEntity);
		}

		return new Result<Integer>(pauseDuration);
	}

	/**
	 * 项目事项暂停列表
	 *
	 * @param prjTaskInstId
	 *            项目事项ID
	 * @return
	 */
	@Override
	public ListResult<PrjTaskPauseDTO> findList(long prjTaskInstId) {
		PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
		example.createCriteria().andPrjTaskInstIdEqualTo(prjTaskInstId);
		List<PrjTaskPauseDetail> list = mapper.selectByExample(example);

		List<PrjTaskPauseDTO> listDto = new ArrayList<>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, listDto, PrjTaskPauseDTO.class);
		}

		ListResult<PrjTaskPauseDTO> result = new ListResult<PrjTaskPauseDTO>();
		result.setObj(listDto);

		return result;
	}
}