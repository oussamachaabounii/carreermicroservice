package com.talenteo.career.model.entity;

import com.talenteo.career.dto.BiAnnualAssessmentStatus;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "biannual_assessment")
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Builder
public class BiannualAssessment {


    @EmbeddedId
    private BiannualAssessmentId id;

    /* Competence technique */
    private String technicalSkill;
    /* commentaire Competence technique  */
    private String technicalSkillComment;
    /* Competence organisationelle */
    private String organisationalSkill;
    /* commentaire Competence organisationelle */
    private String organisationalSkillComment;
    /*  fonctionelle / sectorielle */
    private String functionalIndustryCompetency;
    /* commentaire fonctionelle / sectorielle */
    private String functionalIndustryCompetencyComment;

    /* Capacité d'encadrement */
    private String managementAbility;
    /* Commentaire Capacité d'encadrement */
    private String managementAbilityComment;
    /* Relationnele client */
    private String customerRelationship;
    /* Commentaire Relationnele client */
    private String customerRelationshipComment;
    /* corporate */
    private String corporate;
    /* Commentaire corporate */
    private String corporateComment;
    /* assiduité */
    private String attendance;
    /* Commentaire assiduité */
    private String attendanceComment;
    /* qualités livrables */
    private String deliverableQuality;
    /* Commentaire qualités livrables */
    private String deliverableQualityComment;
    /* Esprit d'équipe */
    private String teamSpirit;
    /* Commentaire Esprit d'équipe */
    private String teamSpiritComment;

    /* note globale performance */
    private String globalPerformanceRating;
    /* score */
    private Integer score;
    /* status */
    private String status;
    /* Commentaire et évaluation */
    private String comment;

    /* support fonctionnel */
    private Boolean sfEvolutivity;
    /* business manager */
    private Boolean bmEvolutivity;
    /* formateur */
    private Boolean internalTrainerPotential;

    private Date creationDate;
    private Date lastUpdate;
    private Long userUpdate;
    private Integer version;


    @OneToMany(mappedBy = "id.biannualAssessment")
    private List<BiannualAssessmentSkill> biannualAssessmentSkills = new ArrayList<>();

}
