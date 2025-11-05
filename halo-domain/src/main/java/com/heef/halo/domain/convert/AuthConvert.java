package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * auth-user-role转化器
 *
 * @author heefM
 * @date 2025-10-31
 */
@Mapper(componentModel = "spring")  // 添加这一行,注入到spring的bean对象,通过注入使用
public interface AuthConvert {

//    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class); //通过静态方法使用

    // DTO转Entity
    AuthUser toEntity(AuthUserDTO authUserDTO);

    // Entity转DTO
    AuthUserDTO toDto(AuthUser authUser);

    // DTO列表转Entity列表
    List<AuthUser> toEntityList(List<AuthUserDTO> authUserDTOList);

    // Entity列表转DTO列表
    List<AuthUserDTO> toDtoList(List<AuthUser> authUserList);
}
