package com.lpcode.modules.service.impl.form;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.*;
import com.lpcode.modules.service.project.dto.formInstance.caishen.CsGcxmGsysjsVo;
import com.lpcode.modules.service.project.dto.formInstance.guihua.*;
import com.lpcode.modules.service.project.dto.formInstance.huanbao.*;
import com.lpcode.modules.service.project.dto.formInstance.renfang.RfBjshVo;
import com.lpcode.modules.service.project.dto.formInstance.renfang.RfJgyszhbVo;
import com.lpcode.modules.service.project.dto.formInstance.renfang.RfYdjsBjspVo;
import com.lpcode.modules.service.project.dto.formInstance.renfang.RfYdjsJgsbVo;
import com.lpcode.modules.service.project.dto.pinstance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.form
 * @fileName FactoryFormService
 * @date 16/4/26.
 */

@Service
public class FactoryFormServiceImpl implements IFactoryFormService {
	@Autowired
	private IRfBjshService rfBjshService;
	@Autowired
	private IRfYdjsBjspService rfYdjsBjspService;
	@Autowired
	private IRfYdjsJgsbService rfYdjsJgsbService;
	@Autowired
	private IRfJsxmqkbService rfJsxmqkbService;
	@Autowired
	private IRfJgyszhbService rfJgyszhbService;
	@Autowired
	private IXfJsgcSjshsbbService xfJsgcSjshsbbService;
	@Autowired
	private IGtSqwtsFrdbzmsService gtSqwtsFrdbzmsService;
	@Autowired
	private IZjSgqyNmggzService zjSgqyNmggzService;
	@Autowired
	private IZjGczlZszrcnsService zjGczlZszrcnsService;
	@Autowired
	private IZjFddbrsqsService zjFddbrsqsService;
	@Autowired
	private IZjJsgcXmjnkService zjJsgcXmjnkService;
	@Autowired
	private IZjJsgcZljdzcbService zjJsgcZljdzcbService;
	@Autowired
	private IGhJsgcGhxksqbService ghJsgcGhxksqbService;
	@Autowired
	private IGhJsgcGhyssqbService ghJsgcGhyssqbService;
	@Autowired
	private IGhJsgcSzGhxkService ghJsgcSzGhxkService;
	@Autowired
	private IGhJsgcSzGhysService ghJsgcSzGhysService;
	@Autowired
	private IGhJsxmGhxzService ghJsxmGhxzService;
	@Autowired
	private IGhJsydGhxkService ghJsydGhxkService;
	@Autowired
	private IGhSqwtsService ghSqwtsService;
	@Autowired
	private IGtJsydSqbService gtJsydSqbService;
	@Autowired
	private IQxSjshSqsService qxSjshSqsService;
	@Autowired
	private IQxSjshSbbService qxSjshSbbService;
	@Autowired
	private IQxSqsService qxSqsService;
	@Autowired
	private IQxSjjsPjxxService qxSjjsPjxxService;
	@Autowired
	private IQxPjjcWtsService qxPjjcWtsService;
	@Autowired
	private IQxJgysSqsService qxJgysSqsService;
	@Autowired
	private IZjAqscCnsService zjAqscCnsService;
	@Autowired
	private IZjSgxkSqbService zjSgxkSqbService;
	@Autowired
	private IHbJsxmDscyService hbJsxmDscyService;
	@Autowired
	private IHbJsxmGyxmService hbJsxmGyxmService;
	@Autowired
	private IHbJsxmJjxmService hbJsxmJjxmService;
	@Autowired
	private ICsGcxmGsysjsService csGcxmGsysjsService;

	public IBaseFormService getFormScene(String scene) {
		if (scene.equals(this.rfBjshService.getScene().toUpperCase()))
			return this.rfBjshService;
		else if (scene.equals(this.rfYdjsBjspService.getScene().toUpperCase()))
			return this.rfYdjsBjspService;
		else if(scene.equals(this.rfYdjsJgsbService.getScene().toUpperCase()))
			return this.rfYdjsJgsbService;
		else if(scene.equals(this.rfJsxmqkbService.getScene().toUpperCase()))
			return this.rfJsxmqkbService;
		else if(scene.equals(this.rfJgyszhbService.getScene().toUpperCase()))
			return this.rfJgyszhbService;
		else if (scene.equals(this.xfJsgcSjshsbbService.getScene().toUpperCase()))
			return this.xfJsgcSjshsbbService;
		else if (scene.equals(this.gtSqwtsFrdbzmsService.getScene().toUpperCase()))
			return this.gtSqwtsFrdbzmsService;
		else if (scene.equals(this.zjSgqyNmggzService.getScene().toUpperCase()))
			return this.zjSgqyNmggzService;
		else if (scene.equals(this.zjGczlZszrcnsService.getScene().toUpperCase()))
			return this.zjGczlZszrcnsService;
		else if (scene.equals(this.zjFddbrsqsService.getScene().toUpperCase()))
			return this.zjFddbrsqsService;
		else if (scene.equals(this.zjJsgcXmjnkService.getScene().toUpperCase()))
			return this.zjJsgcXmjnkService;
		else if (scene.equals(this.zjJsgcZljdzcbService.getScene().toUpperCase()))
			return this.zjJsgcZljdzcbService;
		else if (scene.equals(this.ghJsgcGhxksqbService.getScene().toUpperCase()))
			return this.ghJsgcGhxksqbService;
		else if (scene.equals(this.ghJsgcGhyssqbService.getScene().toUpperCase()))
			return this.ghJsgcGhyssqbService;
		else if (scene.equals(this.ghJsgcSzGhxkService.getScene().toUpperCase()))
			return this.ghJsgcSzGhxkService;
		else if (scene.equals(this.ghJsgcSzGhysService.getScene().toUpperCase()))
			return this.ghJsgcSzGhysService;
		else if (scene.equals(this.ghJsxmGhxzService.getScene().toUpperCase()))
			return this.ghJsxmGhxzService;
		else if (scene.equals(this.ghJsydGhxkService.getScene().toUpperCase()))
			return this.ghJsydGhxkService;
		else if (scene.equals(this.ghSqwtsService.getScene().toUpperCase()))
			return this.ghSqwtsService;
		else if (scene.equals(this.gtJsydSqbService.getScene().toUpperCase()))
			return this.gtJsydSqbService;
		else if (scene.equals(this.qxSjshSqsService.getScene().toUpperCase()))
			return this.qxSjshSqsService;
		else if (scene.equals(this.qxSjshSbbService.getScene().toUpperCase()))
			return this.qxSjshSbbService;
		else if (scene.equals(this.qxSqsService.getScene().toUpperCase()))
			return this.qxSqsService;
		else if (scene.equals(this.qxSjjsPjxxService.getScene().toUpperCase()))
			return this.qxSjjsPjxxService;
		else if (scene.equals(this.qxPjjcWtsService.getScene().toUpperCase()))
			return this.qxPjjcWtsService;
		else if (scene.equals(this.qxJgysSqsService.getScene().toUpperCase()))
			return this.qxJgysSqsService;
		else if (scene.equals(this.zjAqscCnsService.getScene().toUpperCase()))
			return this.zjAqscCnsService;
		else if (scene.equals(this.zjSgxkSqbService.getScene().toUpperCase()))
			return this.zjSgxkSqbService;
		else if (scene.equals(this.hbJsxmDscyService.getScene().toUpperCase()))
			return this.hbJsxmDscyService;
		else if (scene.equals(this.hbJsxmGyxmService.getScene().toUpperCase()))
			return this.hbJsxmGyxmService;
		else if (scene.equals(this.hbJsxmJjxmService.getScene().toUpperCase()))
			return this.hbJsxmJjxmService;
		else if (scene.equals(this.csGcxmGsysjsService.getScene().toUpperCase()))
			return this.csGcxmGsysjsService;
		return null;
	}

	@Override
	public PrjFormVO initBizForm(PrjInstanceVo prjInstanceVo,String scene,String taskDefId){
		IBaseFormService baseFormService = getFormScene(scene);
		return baseFormService.viewForm(prjInstanceVo,taskDefId);
	}

	@Override
	public PrjFormVO checkBizForm(String scene,Long prjectId,String taskDefId){
		IBaseFormService baseFormService = getFormScene(scene);
		return baseFormService.getByPrjcetId(prjectId,taskDefId);
	}

	/**
	 * 判断所属类为哪一个，强转为某个类后，初始化ID存入数据库
	 */
	@Override
	public void saveOrUpdateBizForm(Object object, String scene, Long id) {
		IBaseFormService baseFormService = getFormScene(scene);
		if (scene.equals(this.rfBjshService.getScene().toUpperCase())){
			FormRfBjshVo form = (FormRfBjshVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.rfYdjsBjspService.getScene().toUpperCase())){
			FormRfYdjsBjspVo form = (FormRfYdjsBjspVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if(scene.equals(this.rfYdjsJgsbService.getScene().toUpperCase())){
			FormRfYdjsJgsbVo form = (FormRfYdjsJgsbVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if(scene.equals(this.rfJsxmqkbService.getScene().toUpperCase())){
			FormRfJsxmqkbVo form = (FormRfJsxmqkbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormRfJsxmqkbQkVo> conList = form.getConList();
			for (FormRfJsxmqkbQkVo formRfJsxmqkbQkVo : conList) {
				formRfJsxmqkbQkVo.setcId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if(scene.equals(this.rfJgyszhbService.getScene().toUpperCase())){
			FormRfJgyszhbVo form = (FormRfJgyszhbVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.xfJsgcSjshsbbService.getScene().toUpperCase())){
			FormXfJsgcSjshsbbVo form = (FormXfJsgcSjshsbbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormXfJsgcSjshsbbDwVo> dwList = form.getDwList();
			for (FormXfJsgcSjshsbbDwVo formXfJsgcSjshsbbDwVo : dwList) {
				formXfJsgcSjshsbbDwVo.setUnitId(null);
			}
			List<FormXfJsgcSjshsbbJzVo> jzList = form.getJzList();
			for (FormXfJsgcSjshsbbJzVo formXfJsgcSjshsbbJzVo : jzList) {
				formXfJsgcSjshsbbJzVo.setBuildId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.gtSqwtsFrdbzmsService.getScene().toUpperCase())){
			FormGtSqwtsFrdbzmsVo form = (FormGtSqwtsFrdbzmsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjSgqyNmggzService.getScene().toUpperCase())){
			FormZjSgqyNmggzVo form = (FormZjSgqyNmggzVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjGczlZszrcnsService.getScene().toUpperCase())){
			FormZjGczlZszrcnsVo form = (FormZjGczlZszrcnsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjFddbrsqsService.getScene().toUpperCase())){
			FormZjFddbrsqsVo form = (FormZjFddbrsqsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjJsgcXmjnkService.getScene().toUpperCase())){
			FormZjJsgcXmjnkVo form = (FormZjJsgcXmjnkVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjJsgcZljdzcbService.getScene().toUpperCase())){
			FormZjJsgcZljdzcbVo form = (FormZjJsgcZljdzcbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormZjJsgcZljdzcbJsVo> buiList = form.getBuiList();
			for (FormZjJsgcZljdzcbJsVo formZjJsgcZljdzcbJsVo : buiList) {
				formZjJsgcZljdzcbJsVo.setbId(null);
			}
			List<FormZjJsgcZljdzcbSgVo> conList = form.getConList();
			for (FormZjJsgcZljdzcbSgVo formZjJsgcZljdzcbSgVo : conList) {
				formZjJsgcZljdzcbSgVo.setcId(null);
			}
			List<FormZjJsgcZljdzcbJlVo> supList = form.getSupList();
			for (FormZjJsgcZljdzcbJlVo formZjJsgcZljdzcbJlVo : supList) {
				formZjJsgcZljdzcbJlVo.setsId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsgcGhxksqbService.getScene().toUpperCase())){
			FormGhJsgcGhxksqbVo form = (FormGhJsgcGhxksqbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormGhJsgcGhxksqbSjVo> desList = form.getDesList();
			for (FormGhJsgcGhxksqbSjVo formGhJsgcGhxksqbSjVo : desList) {
				formGhJsgcGhxksqbSjVo.setdId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsgcGhyssqbService.getScene().toUpperCase())){
			FormGhJsgcGhyssqbVo form = (FormGhJsgcGhyssqbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormGhJsgcGhyssqbJgVo> comList = form.getComList();
			for (FormGhJsgcGhyssqbJgVo formGhJsgcGhyssqbJgVo : comList) {
				formGhJsgcGhyssqbJgVo.setcId(null);
			}
			List<FormGhJsgcGhyssqbClVo> metList = form.getMetList();
			for (FormGhJsgcGhyssqbClVo formGhJsgcGhyssqbClVo : metList) {
				formGhJsgcGhyssqbClVo.setmId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsgcSzGhxkService.getScene().toUpperCase())){
			FormGhJsgcSzGhxkVo form = (FormGhJsgcSzGhxkVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormGhJsgcSzGhxkGxVo> pipList = form.getPipList();
			for (FormGhJsgcSzGhxkGxVo formGhJsgcSzGhxkGxVo : pipList) {
				formGhJsgcSzGhxkGxVo.setpId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsgcSzGhysService.getScene().toUpperCase())){
			FormGhJsgcSzGhysVo form = (FormGhJsgcSzGhysVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormGhJsgcSzGhysClVo> metList = form.getMetList();
			for (FormGhJsgcSzGhysClVo formGhJsgcSzGhysClVo : metList) {
				formGhJsgcSzGhysClVo.setmId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsxmGhxzService.getScene().toUpperCase())){
			FormGhJsxmGhxzVo form = (FormGhJsxmGhxzVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghJsydGhxkService.getScene().toUpperCase())){
			FormGhJsydGhxkVo form = (FormGhJsydGhxkVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.ghSqwtsService.getScene().toUpperCase())){
			FormGhSqwtsVo form = (FormGhSqwtsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.gtJsydSqbService.getScene().toUpperCase())){
			FormGtJsydSqbVo form = (FormGtJsydSqbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormGtJsydSqbJsVo> buiList = form.getBuiList();
			for (FormGtJsydSqbJsVo formGtJsydSqbJsVo : buiList) {
				formGtJsydSqbJsVo.setbId(null);
			}
			List<FormGtJsydSqbZlVo> metList = form.getMetList();
			for (FormGtJsydSqbZlVo formGtJsydSqbZlVo : metList) {
				formGtJsydSqbZlVo.setdId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxSjshSqsService.getScene().toUpperCase())){
			FormQxSjshSqsVo form = (FormQxSjshSqsVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormQxSjshSqsDzVo> eleList = form.getEleList();
			for (FormQxSjshSqsDzVo formQxSjshSqsDzVo : eleList) {
				formQxSjshSqsDzVo.seteId(null);
			}
			List<FormQxSjshSqsYrVo> flaList = form.getFlaList();
			for (FormQxSjshSqsYrVo formQxSjshSqsYrVo : flaList) {
				formQxSjshSqsYrVo.setfId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxSjshSbbService.getScene().toUpperCase())){
			FormQxSjshSbbVo form = (FormQxSjshSbbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormQxSjshSbbJzVo> buiList = form.getBuiList();
			for (FormQxSjshSbbJzVo formQxSjshSbbJzVo : buiList) {
				formQxSjshSbbJzVo.setbId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxSqsService.getScene().toUpperCase())){
			FormQxSqsVo form = (FormQxSqsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxSjjsPjxxService.getScene().toUpperCase())){
			FormQxSjjsPjxxVo form = (FormQxSjjsPjxxVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxPjjcWtsService.getScene().toUpperCase())){
			FormQxPjjcWtsVo form = (FormQxPjjcWtsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.qxJgysSqsService.getScene().toUpperCase())){
			FormQxJgysSqsVo form = (FormQxJgysSqsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjAqscCnsService.getScene().toUpperCase())){
			FormZjAqscCnsVo form = (FormZjAqscCnsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.zjSgxkSqbService.getScene().toUpperCase())){
			FormZjSgxkSqbVo form = (FormZjSgxkSqbVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormZjSgxkSqbJsVo> buiList = form.getBuiList();
			for (FormZjSgxkSqbJsVo formZjSgxkSqbJsVo : buiList) {
				formZjSgxkSqbJsVo.setbId(null);
			}
			List<FormZjSgxkSqbSgVo> conList = form.getConList();
			for (FormZjSgxkSqbSgVo formZjSgxkSqbSgVo : conList) {
				formZjSgxkSqbSgVo.setcId(null);
			}
			List<FormZjSgxkSqbJlVo> supList = form.getSupList();
			for (FormZjSgxkSqbJlVo formZjSgxkSqbJlVo : supList) {
				formZjSgxkSqbJlVo.setsId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.hbJsxmDscyService.getScene().toUpperCase())){
			FormHbJsxmDscyVo form = (FormHbJsxmDscyVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.hbJsxmGyxmService.getScene().toUpperCase())){
			FormHbJsxmGyxmVo form = (FormHbJsxmGyxmVo) object;
			form.setPrjId(id);
			form.setId(null);
			List<FormHbJsxmGyxmCpVo> proList = form.getProList();
			for (FormHbJsxmGyxmCpVo formHbJsxmGyxmCpVo : proList) {
				formHbJsxmGyxmCpVo.setpId(null);
			}
			List<FormHbJsxmGyxmClVo> metList = form.getMetList();
			for (FormHbJsxmGyxmClVo formHbJsxmGyxmClVo : metList) {
				formHbJsxmGyxmClVo.setmId(null);
			}
			List<FormHbJsxmGyxmSbVo> equList = form.getEquList();
			for (FormHbJsxmGyxmSbVo formHbJsxmGyxmSbVo : equList) {
				formHbJsxmGyxmSbVo.seteId(null);
			}
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.hbJsxmJjxmService.getScene().toUpperCase())){
			FormHbJsxmJjxmVo form = (FormHbJsxmJjxmVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}else if (scene.equals(this.csGcxmGsysjsService.getScene().toUpperCase())){
			FormCsGcxmGsysjsVo form = (FormCsGcxmGsysjsVo) object;
			form.setPrjId(id);
			form.setId(null);
			baseFormService.saveOrUpdateForm(form);
		}
	}

	@Override
	public Object FormVoForCreateLpcode(Object object, String scene) {
		if (scene.equals(this.hbJsxmDscyService.getScene().toUpperCase())){
			FormHbJsxmDscyVo form = (FormHbJsxmDscyVo) object;
			HbJsxmDscyVo hbJsxmDscyVo = new HbJsxmDscyVo();
			BeanCopy.copyProperties(form, hbJsxmDscyVo, HbJsxmDscyVo.class);
			object = hbJsxmDscyVo;
		}else if(scene.equals(this.hbJsxmGyxmService.getScene().toUpperCase())){
			FormHbJsxmGyxmVo form = (FormHbJsxmGyxmVo) object;
			HbJsxmGyxmVo hbJsxmGyxmVo = new HbJsxmGyxmVo();
			BeanCopy.copyProperties(form, hbJsxmGyxmVo, HbJsxmGyxmVo.class);
			List<HbJsxmGyxmCpVo> cpList = new ArrayList<HbJsxmGyxmCpVo>();
			List<HbJsxmGyxmClVo> clList = new ArrayList<HbJsxmGyxmClVo>();
			List<HbJsxmGyxmSbVo> sbList = new ArrayList<HbJsxmGyxmSbVo>();
			List<FormHbJsxmGyxmCpVo> proList = form.getProList();
			BeanCopy.copyPropertiesForList(proList, cpList, HbJsxmGyxmCpVo.class);
			hbJsxmGyxmVo.setProList(cpList);
			List<FormHbJsxmGyxmClVo> metList = form.getMetList();
			BeanCopy.copyPropertiesForList(metList, clList, HbJsxmGyxmClVo.class);
			hbJsxmGyxmVo.setMetList(clList);
			List<FormHbJsxmGyxmSbVo> equList = form.getEquList();
			BeanCopy.copyPropertiesForList(equList, sbList, HbJsxmGyxmSbVo.class);
			hbJsxmGyxmVo.setEquList(sbList);
			object = hbJsxmGyxmVo;
		}else if(scene.equals(this.hbJsxmJjxmService.getScene().toUpperCase())){
			FormHbJsxmJjxmVo form = (FormHbJsxmJjxmVo) object;
			HbJsxmJjxmVo hbJsxmJjxmVo = new HbJsxmJjxmVo();
			BeanCopy.copyProperties(form, hbJsxmJjxmVo, HbJsxmJjxmVo.class);
			object = hbJsxmJjxmVo;
		}else if(scene.equals(this.ghJsgcGhxksqbService.getScene().toUpperCase())){
			FormGhJsgcGhxksqbVo form = (FormGhJsgcGhxksqbVo) object;
			GhJsgcGhxksqbVo ghJsgcGhxksqbVo = new GhJsgcGhxksqbVo();
			BeanCopy.copyProperties(form, ghJsgcGhxksqbVo, GhJsgcGhxksqbVo.class);
			List<GhJsgcGhxksqbSjVo> sjList = new ArrayList<GhJsgcGhxksqbSjVo>();
			List<FormGhJsgcGhxksqbSjVo> desList = form.getDesList();
			BeanCopy.copyPropertiesForList(desList, sjList, GhJsgcGhxksqbSjVo.class);
			ghJsgcGhxksqbVo.setDesList(sjList);
			object = ghJsgcGhxksqbVo;
		}else if(scene.equals(this.ghJsgcGhyssqbService.getScene().toUpperCase())){
			FormGhJsgcGhyssqbVo form = (FormGhJsgcGhyssqbVo) object;
			GhJsgcGhyssqbVo ghJsgcGhyssqbVo = new GhJsgcGhyssqbVo();
			BeanCopy.copyProperties(form, ghJsgcGhyssqbVo, GhJsgcGhyssqbVo.class);
			List<GhJsgcGhyssqbJgVo> jgList = new ArrayList<GhJsgcGhyssqbJgVo>();
			List<GhJsgcGhyssqbClVo> clList = new ArrayList<GhJsgcGhyssqbClVo>();
			List<FormGhJsgcGhyssqbJgVo> comList = form.getComList();
			BeanCopy.copyPropertiesForList(comList, jgList, GhJsgcGhyssqbJgVo.class);
			ghJsgcGhyssqbVo.setComList(jgList);
			List<FormGhJsgcGhyssqbClVo> metList = form.getMetList();
			BeanCopy.copyPropertiesForList(metList, clList, GhJsgcGhyssqbClVo.class);
			ghJsgcGhyssqbVo.setMetList(clList);
			object = ghJsgcGhyssqbVo;
		}else if(scene.equals(this.ghJsgcSzGhxkService.getScene().toUpperCase())){
			FormGhJsgcSzGhxkVo form = (FormGhJsgcSzGhxkVo) object;
			GhJsgcSzGhxkVo ghJsgcSzGhxkVo = new GhJsgcSzGhxkVo();
			BeanCopy.copyProperties(form, ghJsgcSzGhxkVo, GhJsgcSzGhxkVo.class);
			List<GhJsgcSzGhxkGxVo> gxList = new ArrayList<GhJsgcSzGhxkGxVo>();
			List<FormGhJsgcSzGhxkGxVo> pipList = form.getPipList();
			BeanCopy.copyPropertiesForList(pipList, gxList, GhJsgcSzGhxkGxVo.class);
			ghJsgcSzGhxkVo.setPipList(gxList);
			object = ghJsgcSzGhxkVo;
		}else if(scene.equals(this.ghJsgcSzGhysService.getScene().toUpperCase())){
			FormGhJsgcSzGhysVo form = (FormGhJsgcSzGhysVo) object;
			GhJsgcSzGhysVo ghJsgcSzGhysVo = new GhJsgcSzGhysVo();
			BeanCopy.copyProperties(form, ghJsgcSzGhysVo, GhJsgcSzGhysVo.class);
			List<GhJsgcSzGhysClVo> clList = new ArrayList<GhJsgcSzGhysClVo>();
			List<FormGhJsgcSzGhysClVo> metList = form.getMetList();
			BeanCopy.copyPropertiesForList(metList, clList, GhJsgcSzGhysClVo.class);
			ghJsgcSzGhysVo.setMetList(clList);
			object = ghJsgcSzGhysVo;
		}else if(scene.equals(this.ghJsxmGhxzService.getScene().toUpperCase())){
			FormGhJsxmGhxzVo form = (FormGhJsxmGhxzVo) object;
			GhJsxmGhxzVo ghJsxmGhxzVo = new GhJsxmGhxzVo();
			BeanCopy.copyProperties(form, ghJsxmGhxzVo, GhJsxmGhxzVo.class);
			object = ghJsxmGhxzVo;
		}else if(scene.equals(this.ghJsydGhxkService.getScene().toUpperCase())){
			FormGhJsydGhxkVo form = (FormGhJsydGhxkVo) object;
			GhJsydGhxkVo ghJsydGhxkVo = new GhJsydGhxkVo();
			BeanCopy.copyProperties(form, ghJsydGhxkVo, GhJsydGhxkVo.class);
			object = ghJsydGhxkVo;
		}else if(scene.equals(this.rfYdjsJgsbService.getScene().toUpperCase())){
			FormRfYdjsJgsbVo form = (FormRfYdjsJgsbVo) object;
			RfYdjsJgsbVo rfYdjsJgsbVo = new RfYdjsJgsbVo();
			BeanCopy.copyProperties(form, rfYdjsJgsbVo, RfYdjsJgsbVo.class);
			object = rfYdjsJgsbVo;
		}else if(scene.equals(this.rfBjshService.getScene().toUpperCase())){
			FormRfBjshVo form = (FormRfBjshVo) object;
			RfBjshVo rfBjshVo = new RfBjshVo();
			BeanCopy.copyProperties(form, rfBjshVo, RfBjshVo.class);
			object = rfBjshVo;
		}else if(scene.equals(this.rfJgyszhbService.getScene().toUpperCase())){
			FormRfJgyszhbVo form = (FormRfJgyszhbVo) object;
			RfJgyszhbVo rfJgyszhbVo = new RfJgyszhbVo();
			BeanCopy.copyProperties(form, rfJgyszhbVo, RfJgyszhbVo.class);
			object = rfJgyszhbVo;
		}else if(scene.equals(this.rfYdjsBjspService.getScene().toUpperCase())){
			FormRfYdjsBjspVo form = (FormRfYdjsBjspVo) object;
			RfYdjsBjspVo rfYdjsBjspVo = new RfYdjsBjspVo();
			BeanCopy.copyProperties(form, rfYdjsBjspVo, RfYdjsBjspVo.class);
			object = rfYdjsBjspVo;
		}else if(scene.equals(this.csGcxmGsysjsService.getScene().toUpperCase())){
			FormCsGcxmGsysjsVo form = (FormCsGcxmGsysjsVo) object;
			CsGcxmGsysjsVo csGcxmGsysjsVo = new CsGcxmGsysjsVo();
			BeanCopy.copyProperties(form, csGcxmGsysjsVo, CsGcxmGsysjsVo.class);
			object = csGcxmGsysjsVo;
		}
		return object;
	}

}
