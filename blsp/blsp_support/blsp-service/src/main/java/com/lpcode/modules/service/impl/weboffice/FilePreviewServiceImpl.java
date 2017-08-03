package com.lpcode.modules.service.impl.weboffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.FilePreview;
import com.lpcode.modules.blsp.entity.FilePreviewExample;
import com.lpcode.modules.blsp.mapper.FilePreviewMapper;
import com.lpcode.modules.dto.weboffice.FilePreviewDto;
import com.lpcode.modules.service.weboffice.FilePreviewService;

@Service
@Transactional(readOnly = false)
public class FilePreviewServiceImpl implements FilePreviewService {
	@Autowired
	private FilePreviewMapper filePreviewMapper;
	
	@Override
	public void save(FilePreviewDto filePreviewDto){
		FilePreview filePreview = new FilePreview();
		BeanCopy.copyProperties(filePreviewDto,filePreview);
		filePreviewMapper.insert(filePreview);
	}
	
	@Override
	public ListResult<FilePreviewDto> findFilePreview(String fileUrl){
		ListResult<FilePreviewDto> result = new ListResult<FilePreviewDto>();
		FilePreviewExample example = new FilePreviewExample();
		FilePreviewExample.Criteria criteria = example.createCriteria();
		criteria.andFileUrlEqualTo(fileUrl);
		criteria.andViewUrlIsNotNull();
		List<FilePreview> list = filePreviewMapper.selectByExample(example);
		if(list != null && list.size()>0){
			List<FilePreviewDto> dtoList = new ArrayList<FilePreviewDto>();
			BeanCopy.copyPropertiesForListWithBlank2Null(list,dtoList,FilePreviewDto.class);
			result.setObj(dtoList);
		}
		return result;
	}
	
	
	
}
