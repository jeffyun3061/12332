package com.wellcom.domain.Member.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberListResDto {
    private Long id;
    private String nickName;
    private String email;
}
