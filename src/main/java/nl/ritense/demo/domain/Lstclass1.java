package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Lstclass1.
 */
@Entity
@Table(name = "lstclass_1")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lstclass1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "waarde")
    private Integer waarde;

    @Column(name = "dwhrecordid")
    private Integer dwhrecordid;

    @Column(name = "dwhodsrecordid")
    private Integer dwhodsrecordid;

    @Column(name = "dwhstartdt")
    private LocalDate dwhstartdt;

    @Column(name = "dwheinddt")
    private LocalDate dwheinddt;

    @Column(name = "dwhrunid")
    private Integer dwhrunid;

    @Column(name = "dwhbron")
    private String dwhbron;

    @Column(name = "dwhlaaddt")
    private LocalDate dwhlaaddt;

    @Column(name = "dwhactueel")
    private Integer dwhactueel;

    @Column(name = "lstclass_1_id")
    private Integer lstclass1id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lstclass1 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWaarde() {
        return this.waarde;
    }

    public Lstclass1 waarde(Integer waarde) {
        this.setWaarde(waarde);
        return this;
    }

    public void setWaarde(Integer waarde) {
        this.waarde = waarde;
    }

    public Integer getDwhrecordid() {
        return this.dwhrecordid;
    }

    public Lstclass1 dwhrecordid(Integer dwhrecordid) {
        this.setDwhrecordid(dwhrecordid);
        return this;
    }

    public void setDwhrecordid(Integer dwhrecordid) {
        this.dwhrecordid = dwhrecordid;
    }

    public Integer getDwhodsrecordid() {
        return this.dwhodsrecordid;
    }

    public Lstclass1 dwhodsrecordid(Integer dwhodsrecordid) {
        this.setDwhodsrecordid(dwhodsrecordid);
        return this;
    }

    public void setDwhodsrecordid(Integer dwhodsrecordid) {
        this.dwhodsrecordid = dwhodsrecordid;
    }

    public LocalDate getDwhstartdt() {
        return this.dwhstartdt;
    }

    public Lstclass1 dwhstartdt(LocalDate dwhstartdt) {
        this.setDwhstartdt(dwhstartdt);
        return this;
    }

    public void setDwhstartdt(LocalDate dwhstartdt) {
        this.dwhstartdt = dwhstartdt;
    }

    public LocalDate getDwheinddt() {
        return this.dwheinddt;
    }

    public Lstclass1 dwheinddt(LocalDate dwheinddt) {
        this.setDwheinddt(dwheinddt);
        return this;
    }

    public void setDwheinddt(LocalDate dwheinddt) {
        this.dwheinddt = dwheinddt;
    }

    public Integer getDwhrunid() {
        return this.dwhrunid;
    }

    public Lstclass1 dwhrunid(Integer dwhrunid) {
        this.setDwhrunid(dwhrunid);
        return this;
    }

    public void setDwhrunid(Integer dwhrunid) {
        this.dwhrunid = dwhrunid;
    }

    public String getDwhbron() {
        return this.dwhbron;
    }

    public Lstclass1 dwhbron(String dwhbron) {
        this.setDwhbron(dwhbron);
        return this;
    }

    public void setDwhbron(String dwhbron) {
        this.dwhbron = dwhbron;
    }

    public LocalDate getDwhlaaddt() {
        return this.dwhlaaddt;
    }

    public Lstclass1 dwhlaaddt(LocalDate dwhlaaddt) {
        this.setDwhlaaddt(dwhlaaddt);
        return this;
    }

    public void setDwhlaaddt(LocalDate dwhlaaddt) {
        this.dwhlaaddt = dwhlaaddt;
    }

    public Integer getDwhactueel() {
        return this.dwhactueel;
    }

    public Lstclass1 dwhactueel(Integer dwhactueel) {
        this.setDwhactueel(dwhactueel);
        return this;
    }

    public void setDwhactueel(Integer dwhactueel) {
        this.dwhactueel = dwhactueel;
    }

    public Integer getLstclass1id() {
        return this.lstclass1id;
    }

    public Lstclass1 lstclass1id(Integer lstclass1id) {
        this.setLstclass1id(lstclass1id);
        return this;
    }

    public void setLstclass1id(Integer lstclass1id) {
        this.lstclass1id = lstclass1id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lstclass1)) {
            return false;
        }
        return getId() != null && getId().equals(((Lstclass1) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lstclass1{" +
            "id=" + getId() +
            ", waarde=" + getWaarde() +
            ", dwhrecordid=" + getDwhrecordid() +
            ", dwhodsrecordid=" + getDwhodsrecordid() +
            ", dwhstartdt='" + getDwhstartdt() + "'" +
            ", dwheinddt='" + getDwheinddt() + "'" +
            ", dwhrunid=" + getDwhrunid() +
            ", dwhbron='" + getDwhbron() + "'" +
            ", dwhlaaddt='" + getDwhlaaddt() + "'" +
            ", dwhactueel=" + getDwhactueel() +
            ", lstclass1id=" + getLstclass1id() +
            "}";
    }
}
