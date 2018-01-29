package com.ionwallet.mapper.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractMapper<DTO,ENTITY> {

	@Autowired
	private ModelMapper mapper;

	protected Class<DTO> dtoClazz;

	protected Class<ENTITY> entityClazz;

	@SuppressWarnings("unchecked")
	public AbstractMapper() {
		this.dtoClazz = (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityClazz = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public DTO covertToDto(ENTITY entity){
		DTO dto=mapper.map(entity, dtoClazz);
		return dto;
	}
	

	public ENTITY convertToEntity(DTO dto){
		ENTITY entity=mapper.map(dto,entityClazz);
		return entity;
	}
	
	public List<DTO> covertToDtoList(List<ENTITY> entity){
	    java.lang.reflect.Type targetListType = new TypeToken<List<DTO>>() {}.getType();
		List<DTO> dto=mapper.map(entity, targetListType);
		return dto;
	}
	
	public List<ENTITY> convertToEntityList(List<DTO> dto){
		java.lang.reflect.Type targetListType = new TypeToken<List<ENTITY>>() {}.getType();
		List<ENTITY> entity=mapper.map(dto,targetListType);
		System.out.println(targetListType.toString());
		return entity;
	}
	

}
