/**
 *
 */
package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.framework.core.constants.BaseCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.result.CommonResult;
import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjTaskTransferDetail;
import com.lpcode.modules.blsp.entity.PrjTaskTransferDetailExample;
import com.lpcode.modules.blsp.mapper.PrjTaskTransferDetailMapper;
import com.lpcode.modules.service.project.dto.PrjTaskTransferDetailDTO;
import com.lpcode.modules.service.project.inf.PrjTaskTransferDetailService;

/**
 * 事项流转记录ServiceImpl
 *
 * @author wangyazhou
 *
 */
@Service
@Transactional(readOnly = true)
public class PrjTaskTransferDetailServiceImpl implements PrjTaskTransferDetailService {

	@Autowired
	PrjTaskTransferDetailMapper mapper;

	@Override
	@Transactional(readOnly = false)
	public CommonResult saveTransfer(PrjTaskTransferDetailDTO dto) {

		// 跟新审批结束时间
		if (StringUtils.isNotBlank(dto.getDescription())) {
			updateAuditEndTime(dto.getPrjTaskInstId(), dto.getTransfer(), dto.getDescription(), "0");
		}

		// 插入一条新的数据
		PrjTaskTransferDetail entity = new PrjTaskTransferDetail();
		BeanCopy.copyProperties(dto, entity);
		entity.setDescription("");
		entity.setUpdateTime(null);
		entity.setUpdator("");
		entity.setIsFinished("0");
		entity.setCreator(dto.getTransfer());
		entity.setCreatTime(new Date());
		entity.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
		mapper.insert(entity);

		return new CommonResult();
	}

	/**
	 * 跟新审批结束时间
	 * @param prjTaskInstId 项目事项ID
	 * @param userId 用户ID
	 * @param desc 审批意见
	 * @param isFinish 是否办结 0、未办结；1、已办结；6、审批不通过
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult updateAuditEndTime(long prjTaskInstId, String userId, String desc, String isFinish) {
		PrjTaskTransferDetailExample example = new PrjTaskTransferDetailExample();
		PrjTaskTransferDetailExample.Criteria criteria = example.createCriteria();
		criteria.andPrjTaskInstIdEqualTo(prjTaskInstId);
		criteria.andReceiverEqualTo(userId);
		criteria.andUpdateTimeIsNull();
//		criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		PrjTaskTransferDetail oldEntity = new PrjTaskTransferDetail();
		oldEntity.setUpdateTime(new Date());
		oldEntity.setUpdator(userId);
		oldEntity.setDescription(desc);
		oldEntity.setIsFinished(isFinish);
		oldEntity.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
		mapper.updateByExampleSelective(oldEntity, example);
		return new CommonResult();
	}

	/**
	 * 项目事项审批列表
	 *
	 * @param prjTaskInstId
	 *            项目事项ID
	 * @return
	 */
	@Override
	public ListResult<PrjTaskTransferDetailDTO> findList(long prjTaskInstId) {
		PrjTaskTransferDetailExample example = new PrjTaskTransferDetailExample();
		example.createCriteria().andPrjTaskInstIdEqualTo(prjTaskInstId);
		List<PrjTaskTransferDetail> list = mapper.selectByExample(example);

		List<PrjTaskTransferDetailDTO> listDto = new ArrayList<>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, listDto, PrjTaskTransferDetailDTO.class);
		}

		ListResult<PrjTaskTransferDetailDTO> result = new ListResult<PrjTaskTransferDetailDTO>();
		result.setObj(listDto);

		return result;
	}

	@Override
	public ListResult<PrjTaskTransferDetailDTO> findMyPass(String transfer) {
		PrjTaskTransferDetailExample example = new PrjTaskTransferDetailExample();
		PrjTaskTransferDetailExample.Criteria criteria = example.createCriteria();
		criteria.andReceiverEqualTo(transfer);
		criteria.andIsFinishedEqualTo("0");
		criteria.andUpdateTimeIsNotNull();
//		criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		
		PrjTaskTransferDetailExample.Criteria criteria2 = example.createCriteria();
		criteria2.andReceiverEqualTo(transfer);
		criteria2.andIsFinishedEqualTo("6");
//		criteria2.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		
		example.or(criteria2);
		List<PrjTaskTransferDetail> list = mapper.selectByExample(example);

		List<PrjTaskTransferDetailDTO> listDto = new ArrayList<>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, listDto, PrjTaskTransferDetailDTO.class);
		}

		ListResult<PrjTaskTransferDetailDTO> result = new ListResult<PrjTaskTransferDetailDTO>();
		result.setObj(listDto);

		return result;
	}

	@Override
	public ListResult<PrjTaskTransferDetailDTO> findMyFinish(String transfer, boolean certFlag) {
		PrjTaskTransferDetailExample example = new PrjTaskTransferDetailExample();
		PrjTaskTransferDetailExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(transfer))
			criteria.andReceiverEqualTo(transfer);
		criteria.andIsFinishedEqualTo("1");
//		criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		
		if (certFlag) {
			PrjTaskTransferDetailExample.Criteria criteria2 = example.createCriteria();
			if (StringUtils.isNotBlank(transfer))
				criteria2.andReceiverEqualTo(transfer);
			criteria2.andIsFinishedEqualTo("6");
//			criteria2.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
			example.or(criteria2);
		}

		List<PrjTaskTransferDetail> list = mapper.selectByExample(example);

		List<PrjTaskTransferDetailDTO> listDto = new ArrayList<>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, listDto, PrjTaskTransferDetailDTO.class);
		}

		ListResult<PrjTaskTransferDetailDTO> result = new ListResult<PrjTaskTransferDetailDTO>();
		result.setObj(listDto);

		return result;
	}
}