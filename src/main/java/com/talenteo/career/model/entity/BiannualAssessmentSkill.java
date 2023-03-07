package com.talenteo.career.model.entity;

import com.talenteo.career.model.entity.embedded.BiannualAssessmentSkillId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "biannual_assessement_skill")
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Builder
public class BiannualAssessmentSkill {

    @EmbeddedId
    private BiannualAssessmentSkillId id;

    @Column
    private Float rating;
}

