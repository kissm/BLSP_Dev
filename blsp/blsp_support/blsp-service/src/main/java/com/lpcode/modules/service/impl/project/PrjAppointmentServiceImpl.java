package com.lpcode.modules.service.impl.project;

import com.framework.core.base.page.Page;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjAppointment;
import com.lpcode.modules.blsp.entity.PrjAppointmentExample;
import com.lpcode.modules.blsp.mapper.PrjAppointmentMapper;
import com.lpcode.modules.dto.project.ReqHeader;
import com.lpcode.modules.dto.project.ReqPrjAppoinData;
import com.lpcode.modules.dto.project.ReqPrjAppointment;
import com.lpcode.modules.service.project.dto.PrjAppointmentListDTO;
import com.lpcode.modules.service.project.dto.PrjAppointmentQueryDTO;
import com.lpcode.modules.service.project.inf.PrjAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.project
 * @fileName PrjAppointmentServiceImpl
 * @date 16/3/8.
 */
@Service
public class PrjAppointmentServiceImpl implements PrjAppointmentService {

    @Autowired
    PrjAppointmentMapper mapper;
    @Override
    public int appointment(ReqPrjAppointment reqPrjAppointment) {
        int sum ;
        PrjAppointment record = new PrjAppointment();
        if(checkData(reqPrjAppointment)){
            BeanCopy.copyProperties(reqPrjAppointment.getData(),record,PrjAppointment.class);
            sum = mapper.insert(record);
            if(sum == 1){
                sum = 0;
            }else{
                sum = 99;
            }
        }else{
            sum = 70;
        }
        return sum;
    }

    /**
     * 数据必填项校验
     * @param req
     * @return
     */
    private boolean checkData(ReqPrjAppointment req){
        ReqHeader reqHeader = req.getReqHeader();
        if(StringUtils.isBlank(reqHeader.getVersion())){
            return false;
        }
        if(StringUtils.isBlank(reqHeader.getReqno())){
            return false;
        }
        if(StringUtils.isBlank(reqHeader.getTimestamp())){
            return false;
        }
        if(StringUtils.isBlank(reqHeader.getPlatformId())){
            return false;
        }

        ReqPrjAppoinData record = req.getData();
//        if(StringUtils.isBlank(record.getLpcodeType())){
//            return false;
//        }
        if(StringUtils.isBlank(record.getCertType())){
            return false;
        }
        if(StringUtils.isBlank(record.getCertCode())){
            return false;
        }
        if(StringUtils.isBlank(record.getAppointDateStr())){
            return false;
        }
        if(StringUtils.isBlank(record.getAppintSeq())){
            return false;
        }
        if(StringUtils.isBlank(record.getAppintType())){
            return false;
        }
        if(StringUtils.isBlank(record.getPhoneNum())){
            return false;
        }
        if(StringUtils.isBlank(record.getWindowSeq())){
            return false;
        }
//        if(StringUtils.isBlank(record.getWindowCode())){
//            return false;
//        }
        if(StringUtils.isBlank(record.getName())){
            return false;
        }
        return true;
    }
    

	/**
	 * 查询预约列表
	 */
	@Override
	public PageResult<PrjAppointmentListDTO> findPage(RequestDTOPage<PrjAppointmentQueryDTO> requestPage) {
		PageResult<PrjAppointmentListDTO> pageResult = new PageResult<PrjAppointmentListDTO>();
		Page<PrjAppointmentListDTO> pagedto = new Page<PrjAppointmentListDTO>();
		List<PrjAppointmentListDTO> listdto = new ArrayList<PrjAppointmentListDTO>();
		int pageNo = requestPage.getPage().getPageNo();
		int pageSize = requestPage.getPage().getPageSize();

		Page<PrjAppointment> queryPage = new Page<PrjAppointment>();
		queryPage.setPageNo(pageNo);
		queryPage.setPageSize(pageSize);
		queryPage.setOrderBy("id desc");

		PrjAppointmentExample example = new PrjAppointmentExample();
		PrjAppointmentExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(requestPage.getObj().getCertType()))
			criteria.andCertTypeEqualTo(requestPage.getObj().getCertType());
		if (StringUtils.isNotBlank(requestPage.getObj().getCertCode()))
			criteria.andCertCodeLike("%" + requestPage.getObj().getCertCode() + "%");

		Page<PrjAppointment> selectPage = mapper.pagedSelectByExample(example, queryPage);

		BeanCopy.copyPropertiesForListWithBlank2Null(selectPage.getList(), listdto, PrjAppointmentListDTO.class);

		pagedto.setList(listdto);
		pagedto.setPageNo(pageNo);
		pagedto.setPageSize(pageSize);
		pagedto.setCount(queryPage.getCount());
		pageResult.setObj(pagedto);
		return pageResult;

	}

	
    /**
	 * 根据证件号码查询预约信息
	 * 
	 * @param certCode
	 * @return
	 */
	@Override
	public Result<PrjAppointmentListDTO> findByCertCode(String certCode) {
		Result<PrjAppointmentListDTO> result = new Result<PrjAppointmentListDTO>();
		PrjAppointmentListDTO dto = new PrjAppointmentListDTO();
		PrjAppointmentExample example = new PrjAppointmentExample();
		PrjAppointmentExample.Criteria criteria = example.createCriteria();
		criteria.andCertCodeEqualTo(certCode);
		// criteria.andAppointDateEqualTo(new Date());
		example.setOrderByClause("CREAT_TIME DESC");
		List<PrjAppointment> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			PrjAppointment entity = list.get(0);
			BeanCopy.copyPropertiesWithBlank2Null(entity, dto);
		}
		result.setObj(dto);
		return result;
	}

}
