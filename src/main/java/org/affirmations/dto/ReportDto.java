package org.affirmations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.affirmations.model.Report;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReportDto {
    private String username;
    private String reportedType; // "affirmation", "resource"
    private String reason;

    public static ReportDto fromModel(Report report) {
        return ReportDto.builder()
                .username(report.getUsername())
                .reportedType(report.getReportedType())
                .reason(report.getReason())
                .build();
    }

    public static Report toModel(ReportDto dto) {
        return Report.builder()
                .username(dto.getUsername())
                .reportedType(dto.getReportedType())
                .reason(dto.getReason())
                .build();
    }

}