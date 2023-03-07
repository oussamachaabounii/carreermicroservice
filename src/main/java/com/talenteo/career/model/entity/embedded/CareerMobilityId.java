package com.talenteo.career.model.entity.embedded;

import com.talenteo.career.model.entity.Career;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Embeddable
@Builder
public class CareerMobilityId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "career_id")
    private Career career;

    @Column
    private String mobilityCode;
}