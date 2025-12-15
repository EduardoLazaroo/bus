package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.LinkStatus;
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
    private LinkStatus status;
}
