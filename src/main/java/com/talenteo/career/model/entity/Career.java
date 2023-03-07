package com.talenteo.career.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "career")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder()
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long hr;

    @Column
    private Date lastBa;

    @Column
    private Date lastBmFollowup;

    @Column
    private Date availabilityDate;

    @Column
    private Float monthlySalary;

    @Column
    private Float dailyFee;

    @Column
    private String devise;

    @Column
    private String comment;

    @Column
    private String mobilityComment;

    @Column
    private Long cm;

    @Column
    private Long cma;

    @Column
    private String executiveStatus;

    @Column
    private Date entryDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "id.career")
    private List<CareerMobility> careerMobilities = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "id.career",fetch = FetchType.LAZY,orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<BiannualAssessment> biannualAssessments ;
}
