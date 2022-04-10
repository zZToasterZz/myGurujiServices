package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FeedbackGradingScale",uniqueConstraints = @UniqueConstraint(columnNames = {"GradingName","GradingPoint","GradingPointValues"}))
@NamedQueries({
        @NamedQuery(name="GradingScale.getDistinctScaleName",query ="select distinct GradingName from GradingScale"),
        @NamedQuery(name="GradingScale.getDistinctScaleByName",query ="select gs from GradingScale gs where gs.GradingName=:gradingname"),
        @NamedQuery(name="GradingScale.getDistinctScaleCountByName",query ="select count(gs) from GradingScale gs where gs.GradingName=:gradingname")

})
public class GradingScale extends SharedField implements Serializable {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "GradingScale_Sqr", sequenceName = "GradingScale_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GradingScale_Sqr")
    private long GradingScaleId;

    @Column(length = 50)
    private String GradingName;
    @Column(length = 10)
    private String GradingPoint;
    @Column(length = 80)
    private String GradingPointValues;

    public GradingScale() {
    }

    public GradingScale(long gradingScaleId, String gradingName, String gradingPoint, String gradingPointValues) {
        GradingScaleId = gradingScaleId;
        GradingName = gradingName;
        GradingPoint = gradingPoint;
        GradingPointValues = gradingPointValues;
    }

    public long getGradingScaleId() {
        return GradingScaleId;
    }

    public void setGradingScaleId(long gradingScaleId) {
        GradingScaleId = gradingScaleId;
    }

    public String getGradingName() {
        return GradingName;
    }

    public void setGradingName(String gradingName) {
        GradingName = gradingName;
    }

    public String getGradingPoint() {
        return GradingPoint;
    }

    public void setGradingPoint(String gradingPoint) {
        GradingPoint = gradingPoint;
    }

    public String getGradingPointValues() {
        return GradingPointValues;
    }

    public void setGradingPointValues(String gradingPointValues) {
        GradingPointValues = gradingPointValues;
    }
}
