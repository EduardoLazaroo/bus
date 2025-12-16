package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.enums.UserRole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLinkResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long companyId;
    private String companyName;
    private UserRole role;
    private LinkStatus status;
}
