package com.talenteo.career.service;

import com.talenteo.career.client.HrMsClient;
import com.talenteo.career.client.SearchMsClient;
import com.talenteo.career.client.TdMsClient;
import com.talenteo.career.converter.*;
import com.talenteo.career.dto.*;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.repository.AssessmentCampaignRepository;
import com.talenteo.career.repository.BiannualAssessmentRepository;
import com.talenteo.career.repository.CareerRepository;
import com.talenteo.career.repository.MobilityRepository;
import com.talenteo.td.dto.TechnicalDocumentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
@Transactional
public class CareerService {

    private final MobilityRepository mobilityRepository;
    private final CareerRepository careerRepository;
    private final BiannualAssessmentRepository biannualAssessmentRepository;
    private final AssessmentCampaignRepository assessmentCampaignRepository;
    private final TdMsClient tdMsClient;
    private final HrMsClient hrMsClient;
    private final SearchMsClient searchMsClient;


    /**
     * =======================================================
     * Find Career By ID
     * =======================================================
     *
     * @param careerId
     * @return
     */
    public Optional<CareerDto> findCareerById(Long careerId) {
        Career career = careerRepository.findById(careerId).orElse(null);
        return Optional.ofNullable(CareerConverter.newInstance().convert(career));

    }

    /**
     * =======================================================
     * Find career By resource ID
     * =======================================================
     *
     * @param hrId
     * @return
     */
    public Optional<CareerDto> findCareerByResourceId(Long hrId) {
        Optional<Career> career = careerRepository.findByHr(hrId);
        return career.map(CareerConverter.newInstance()::convert);
    }

    /**
     * =======================================================
     * find History of BiannualAssessments By Resource Id
     * =======================================================
     *
     * @param hrId
     * @return
     */
    public List<BiAnnualAssessmentDto> findHistoryBiannualAssessmentsByResourceId(Long hrId) {
        //initiate feign call to TD MS
        Optional<TechnicalDocumentDto> documentDto = Optional.of(tdMsClient.getByHrId(hrId).getBody());
        List<BiannualAssessment> biannualAssessmentList = biannualAssessmentRepository.findByIdCareerHr(hrId);
        log.info("Biannual assessments size {}", biannualAssessmentList.size());

        return biannualAssessmentList.stream().
                map(BiAnnualAssessmentDtoConverter.newInstance()
                        .withTd(documentDto.orElse(TechnicalDocumentDto.builder().build()))::convert)
                .collect(Collectors.toList());
    }

    /**
     * =======================================================
     * find History of BiannualAssessments By Manager Id
     * =======================================================
     *
     * @param cmId
     * @return
     */
    public List<BiAnnualAssessmentDto> findHistoryBiannualAssessmentsByManagerId(Long cmId) {
        //initiate feign call to TD MS
        //Optional<TechnicalDocumentDto> documentDto = Optional.of(tdMsClient.getByHrId(id).getBody());
        List<BiannualAssessment> biannualAssessmentList = biannualAssessmentRepository.findByIdCareerCm(cmId);
        log.info("Biannual assessments size {}", biannualAssessmentList.size());

        return biannualAssessmentList.stream().
                map(BiAnnualAssessmentDtoConverter.newInstance()::convert)
                .collect(Collectors.toList());
    }

    /**
     * =======================================================
     * Find latest Assessment campaign
     * =======================================================
     *
     * @return
     */
    public AssessmentCampaignDto findLatestAssessmentCampaign() {
        AssessmentCampaign assessmentCampaign = assessmentCampaignRepository.findFirstByOrderByEndDateDesc();
        return CompaignConverterDto.newInstance().convert(assessmentCampaign);
    }


    /**
     * =======================================================
     * Find Career By CM
     * =======================================================
     *
     * @param cmId
     * @return
     */
    public List<CareerDto> findCareerByCm(Long cmId) {
        List<Career> careerList = careerRepository.findByCm(cmId);
        return careerList.stream().map(career -> CareerConverter.newInstance().convert(career)).collect(Collectors.toList());
    }

    /**
     * =======================================================
     * create a new  biannual assessment
     * =======================================================
     *
     * @param baRequestDto
     * @return
     */
    public BiAnnualAssessmentDto saveBiannualAssessment(BiannualAssessmentRequestDto baRequestDto) {
        BiannualAssessment biannualAssessment = BiannualAssessmentConverter.newInstance().convert(baRequestDto);
        BiannualAssessment assessmentSaved = biannualAssessmentRepository.save(biannualAssessment);
        return BiAnnualAssessmentDtoConverter.newInstance().convert(assessmentSaved);
    }

    public AssessmentCampaignDto saveAssessmentCampaign(AssessmentCampaignRequestDto assessmentRequestDto) {
        AssessmentCampaign assessmentCampaign = AssessmentCampaignConverter.newInstance().convert(assessmentRequestDto);
        AssessmentCampaign assessmentSaved = assessmentCampaignRepository.save(assessmentCampaign);
        return AssessmentCampaignDtoConverter.newInstance().convert(assessmentSaved);
    }

    /**
     * =======================================================
     * create a new  career
     * =======================================================
     *
     * @param careerRequestDto
     * @return
     */
    public CareerDto saveCareer(CareerRequestDto careerRequestDto) {
        Career career = CareerRequestConverter.newInstance().convert(careerRequestDto);
        Career savedCareer = careerRepository.save(career);
        indexElasticSearch(savedCareer);
        return CareerConverter.newInstance().convert(savedCareer);
    }

    /**
     * Index created career in elastic Search database
     *
     * @param career
     */
    public void indexElasticSearch(Career career) {
        try {
            this.searchMsClient.indexCareer(career);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * =======================================================
     * Count the number of BiannualAssessments associated
     * with the cmId and check if they are done for the current campaign
     * =======================================================
     *
     * @param cmId
     * @return
     */
    public BiannualAssessmentsCountDto countBiannualAssessments(Long cmId) {
        List<Career> careers = careerRepository.findByCm(cmId);
        int baTodo = 0;
        int baReleased = 0;

        if (careers != null) {
            AssessmentCampaign assessmentCampaign = assessmentCampaignRepository.findFirstByOrderByEndDateDesc();

            if (assessmentCampaign != null) {
                int baTotal = careers.size();
                careers = careers.stream().filter(a -> isBiannualAssessmentDoneForCampaign(a.getBiannualAssessments(), assessmentCampaign)).collect(Collectors.toList());
                baReleased = careers.size();
                baTodo = baTotal - baReleased;
            }
        }


        return BiannualAssessmentsCountDto.builder().todo(baTodo).released(baReleased).build();
    }

    public Map<String, Long> statsBiannualAssessments(Long hrId) {

        long baFinished = 0;
        long baInprogress = 0;
        long baNotStarted = 0;

        List<BiAnnualAssessmentDto> biannualAssessments = findHistoryBiannualAssessmentsByManagerId(hrId);
        baFinished = biannualAssessments.stream().filter(biannualAssessment -> BiAnnualAssessmentStatus.Finished.equals(biannualAssessment.getStatus())).count();
        baInprogress = biannualAssessments.stream().filter(biannualAssessment -> BiAnnualAssessmentStatus.InProgress.equals(biannualAssessment.getStatus())).count();
        baNotStarted = biannualAssessments.stream().filter(biannualAssessment -> BiAnnualAssessmentStatus.NotStarted.equals(biannualAssessment.getStatus())).count();


        Map<String, Long> stats = new HashMap<String, Long>();
        stats.put("FinishedAssessements", baFinished);
        stats.put("InProgressAssessements", baInprogress);
        stats.put("NotStartedAssessements", baNotStarted);

        return stats;
    }

    public List<AssessmentCampaignDto> getAllByCompanyId(Long companyId) {
        List<AssessmentCampaign> assessmentCampaigns = assessmentCampaignRepository.findByIdCompanyId(companyId);
        return assessmentCampaigns.stream().map(AssessmentCampaign -> AssessmentCampaignDtoConverter.newInstance().convert(AssessmentCampaign)).collect(Collectors.toList());
    }

    /**
     * =======================================================
     * Check if one of the biannualAssessments
     * correspond to the current campaign.
     * =======================================================
     *
     * @param biannualAssessments
     * @param lastAssessmentCampaign
     * @return true if one of the assessments belongs to the input campaign, false otherwise
     */
    private boolean isBiannualAssessmentDoneForCampaign(List<BiannualAssessment> biannualAssessments, AssessmentCampaign lastAssessmentCampaign) {

        return biannualAssessments.stream().anyMatch(ba -> lastAssessmentCampaign.getId().equals(ba.getId().getAssessmentCampaign().getId()));
    }

    /**
     * =======================================================
     * find careers by manager ID
     * =======================================================
     *
     * @param managerId
     * @return
     */
    public List<CareerDto> findCareersByManagerId(Long managerId) {

        return careerRepository.findByCm(managerId).stream().map(career -> CareerConverter.newInstance().convert(career)).collect(Collectors.toList());
    }


    public BiAnnualAssessmentDto updateBiannualAssessment(Integer semester, Integer year, Long careerId, BiannualAssessmentRequestDto assessmentRequestDto) {
        BiannualAssessment biannualAssessment = biannualAssessmentRepository.findByAssessmentCampaignAndCareer(semester, year, careerId).orElse(null);
        biannualAssessment.setTechnicalSkill(assessmentRequestDto.getTechnicalSkill());
        biannualAssessment.setTechnicalSkillComment(assessmentRequestDto.getTechnicalSkillComment());
        biannualAssessment.setOrganisationalSkill(assessmentRequestDto.getOrganisationalSkill());
        biannualAssessment.setOrganisationalSkillComment(assessmentRequestDto.getOrganisationalSkillComment());
        biannualAssessment.setFunctionalIndustryCompetency(assessmentRequestDto.getFunctionalIndustryCompetency());
        biannualAssessment.setFunctionalIndustryCompetencyComment(assessmentRequestDto.getFunctionalIndustryCompetencyComment());
        biannualAssessment.setGlobalPerformanceRating(assessmentRequestDto.getGlobalPerformanceRating());
        biannualAssessment.setComment(assessmentRequestDto.getComment());
        biannualAssessment.setScore(assessmentRequestDto.getScore());
        biannualAssessment.setStatus(assessmentRequestDto.getStatus().name());
        biannualAssessment.setManagementAbility(assessmentRequestDto.getManagementAbility());
        biannualAssessment.setManagementAbilityComment(assessmentRequestDto.getManagementAbilityComment());
        biannualAssessment.setCustomerRelationship(assessmentRequestDto.getCustomerRelationship());
        biannualAssessment.setCustomerRelationshipComment(assessmentRequestDto.getCustomerRelationshipComment());
        biannualAssessment.setCorporate(assessmentRequestDto.getCorporate());
        biannualAssessment.setCorporateComment(assessmentRequestDto.getCorporateComment());
        biannualAssessment.setAttendance(assessmentRequestDto.getAttendance());
        biannualAssessment.setAttendanceComment(assessmentRequestDto.getAttendanceComment());
        biannualAssessment.setDeliverableQuality(assessmentRequestDto.getDeliverableQuality());
        biannualAssessment.setDeliverableQualityComment(assessmentRequestDto.getDeliverableQualityComment());
        biannualAssessment.setTeamSpirit(assessmentRequestDto.getTeamSpirit());
        biannualAssessment.setTeamSpiritComment(assessmentRequestDto.getTeamSpiritComment());
        biannualAssessment.setLastUpdate(assessmentRequestDto.getLastUpdate());
        BiannualAssessment assessment = biannualAssessmentRepository.save(biannualAssessment);
        return BiAnnualAssessmentDtoConverter.newInstance().convert(assessment);
    }

    /**
     * =======================================================
     * delete career by id
     * =======================================================
     * @param careerId
     */
    public void deleteCareerById(Long careerId){
        Optional<Career> careerToBeDeleted = careerRepository.findById(careerId);
        careerRepository.delete(careerToBeDeleted.get());

    }
}


