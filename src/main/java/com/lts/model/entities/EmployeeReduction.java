package com.lts.model.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lts.util.json.LocalDateTimeDeserializer;
import com.lts.util.json.LocalDateTimeSerializer;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_reduction")
public final class EmployeeReduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "no")
    private String no;

    @Column(name = "name")
    private String name;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "no_and_date_cim")
    private String noAndDateCim;

    @Column(name = "no_hours_cim")
    private String noHoursCim;

    @Column(name = "no_and_date_decision")
    private String noAndDateDecision;

    @Column(name = "no_days_reduction")
    private String noDaysReduction;

    @Column(name = "no_hours_reduction")
    private String noHoursReduction;

    @Column(name = "no_hours_provided")
    private String noHoursProvided;

    @Column(name = "salary_cim")
    private String salaryCim;

    @Column(name = "salary_cim_reduction")
    private String salaryCimReduction;

    @Column(name = "required_amount")
    private String requiredAmount;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content")
    private String fileContent;

    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at", length = 20)
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNoAndDateCim() {
        return noAndDateCim;
    }

    public void setNoAndDateCim(String noAndDateCim) {
        this.noAndDateCim = noAndDateCim;
    }

    public String getNoHoursCim() {
        return noHoursCim;
    }

    public void setNoHoursCim(String noHoursCim) {
        this.noHoursCim = noHoursCim;
    }

    public String getNoAndDateDecision() {
        return noAndDateDecision;
    }

    public void setNoAndDateDecision(String noAndDateDecision) {
        this.noAndDateDecision = noAndDateDecision;
    }

    public String getNoDaysReduction() {
        return noDaysReduction;
    }

    public void setNoDaysReduction(String noDaysReduction) {
        this.noDaysReduction = noDaysReduction;
    }

    public String getNoHoursReduction() {
        return noHoursReduction;
    }

    public void setNoHoursReduction(String noHoursReduction) {
        this.noHoursReduction = noHoursReduction;
    }

    public String getNoHoursProvided() {
        return noHoursProvided;
    }

    public void setNoHoursProvided(String noHoursProvided) {
        this.noHoursProvided = noHoursProvided;
    }

    public String getSalaryCim() {
        return salaryCim;
    }

    public void setSalaryCim(String salaryCim) {
        this.salaryCim = salaryCim;
    }

    public String getSalaryCimReduction() {
        return salaryCimReduction;
    }

    public void setSalaryCimReduction(String salaryCimReduction) {
        this.salaryCimReduction = salaryCimReduction;
    }

    public String getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(String requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
