package com.talenteo.career.model.entity;

import com.talenteo.career.model.entity.embedded.CareerMobilityId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "career_mobility")
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Builder
public class CareerMobility {


    @EmbeddedId
    private CareerMobilityId id;

}