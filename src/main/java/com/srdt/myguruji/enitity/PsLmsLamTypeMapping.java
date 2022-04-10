package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PsLmsLamTypeMapping extends SharedField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "LamType_Sqr", sequenceName = "LamType_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "LamType_Sqr")
    private long LamTypeId;

    private String BatchYear;

    private String BatchSeq;

    private String Term;

    private String PsLamtype;

    private String Description;

    private String DescrShort;

    private String AssessAssignType;

    private String AssessAssignTypeDescr;

    

}
