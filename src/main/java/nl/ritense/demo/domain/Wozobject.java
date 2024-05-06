package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Wozobject.
 */
@Entity
@Table(name = "wozobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wozobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "empty")
    private String empty;

    @Column(name = "datumbegingeldigheidwozobject")
    private LocalDate datumbegingeldigheidwozobject;

    @Column(name = "datumeindegeldigheidwozobject")
    private LocalDate datumeindegeldigheidwozobject;

    @Column(name = "datumwaardepeiling")
    private LocalDate datumwaardepeiling;

    @Column(name = "gebruikscode")
    private String gebruikscode;

    @Column(name = "geometriewozobject")
    private String geometriewozobject;

    @Column(name = "grondoppervlakte")
    private String grondoppervlakte;

    @Column(name = "soortobjectcode")
    private String soortobjectcode;

    @Column(name = "statuswozobject")
    private String statuswozobject;

    @Column(name = "vastgesteldewaarde")
    private String vastgesteldewaarde;

    @Column(name = "wozobjectnummer")
    private String wozobjectnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wozobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpty() {
        return this.empty;
    }

    public Wozobject empty(String empty) {
        this.setEmpty(empty);
        return this;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public LocalDate getDatumbegingeldigheidwozobject() {
        return this.datumbegingeldigheidwozobject;
    }

    public Wozobject datumbegingeldigheidwozobject(LocalDate datumbegingeldigheidwozobject) {
        this.setDatumbegingeldigheidwozobject(datumbegingeldigheidwozobject);
        return this;
    }

    public void setDatumbegingeldigheidwozobject(LocalDate datumbegingeldigheidwozobject) {
        this.datumbegingeldigheidwozobject = datumbegingeldigheidwozobject;
    }

    public LocalDate getDatumeindegeldigheidwozobject() {
        return this.datumeindegeldigheidwozobject;
    }

    public Wozobject datumeindegeldigheidwozobject(LocalDate datumeindegeldigheidwozobject) {
        this.setDatumeindegeldigheidwozobject(datumeindegeldigheidwozobject);
        return this;
    }

    public void setDatumeindegeldigheidwozobject(LocalDate datumeindegeldigheidwozobject) {
        this.datumeindegeldigheidwozobject = datumeindegeldigheidwozobject;
    }

    public LocalDate getDatumwaardepeiling() {
        return this.datumwaardepeiling;
    }

    public Wozobject datumwaardepeiling(LocalDate datumwaardepeiling) {
        this.setDatumwaardepeiling(datumwaardepeiling);
        return this;
    }

    public void setDatumwaardepeiling(LocalDate datumwaardepeiling) {
        this.datumwaardepeiling = datumwaardepeiling;
    }

    public String getGebruikscode() {
        return this.gebruikscode;
    }

    public Wozobject gebruikscode(String gebruikscode) {
        this.setGebruikscode(gebruikscode);
        return this;
    }

    public void setGebruikscode(String gebruikscode) {
        this.gebruikscode = gebruikscode;
    }

    public String getGeometriewozobject() {
        return this.geometriewozobject;
    }

    public Wozobject geometriewozobject(String geometriewozobject) {
        this.setGeometriewozobject(geometriewozobject);
        return this;
    }

    public void setGeometriewozobject(String geometriewozobject) {
        this.geometriewozobject = geometriewozobject;
    }

    public String getGrondoppervlakte() {
        return this.grondoppervlakte;
    }

    public Wozobject grondoppervlakte(String grondoppervlakte) {
        this.setGrondoppervlakte(grondoppervlakte);
        return this;
    }

    public void setGrondoppervlakte(String grondoppervlakte) {
        this.grondoppervlakte = grondoppervlakte;
    }

    public String getSoortobjectcode() {
        return this.soortobjectcode;
    }

    public Wozobject soortobjectcode(String soortobjectcode) {
        this.setSoortobjectcode(soortobjectcode);
        return this;
    }

    public void setSoortobjectcode(String soortobjectcode) {
        this.soortobjectcode = soortobjectcode;
    }

    public String getStatuswozobject() {
        return this.statuswozobject;
    }

    public Wozobject statuswozobject(String statuswozobject) {
        this.setStatuswozobject(statuswozobject);
        return this;
    }

    public void setStatuswozobject(String statuswozobject) {
        this.statuswozobject = statuswozobject;
    }

    public String getVastgesteldewaarde() {
        return this.vastgesteldewaarde;
    }

    public Wozobject vastgesteldewaarde(String vastgesteldewaarde) {
        this.setVastgesteldewaarde(vastgesteldewaarde);
        return this;
    }

    public void setVastgesteldewaarde(String vastgesteldewaarde) {
        this.vastgesteldewaarde = vastgesteldewaarde;
    }

    public String getWozobjectnummer() {
        return this.wozobjectnummer;
    }

    public Wozobject wozobjectnummer(String wozobjectnummer) {
        this.setWozobjectnummer(wozobjectnummer);
        return this;
    }

    public void setWozobjectnummer(String wozobjectnummer) {
        this.wozobjectnummer = wozobjectnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wozobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Wozobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wozobject{" +
            "id=" + getId() +
            ", empty='" + getEmpty() + "'" +
            ", datumbegingeldigheidwozobject='" + getDatumbegingeldigheidwozobject() + "'" +
            ", datumeindegeldigheidwozobject='" + getDatumeindegeldigheidwozobject() + "'" +
            ", datumwaardepeiling='" + getDatumwaardepeiling() + "'" +
            ", gebruikscode='" + getGebruikscode() + "'" +
            ", geometriewozobject='" + getGeometriewozobject() + "'" +
            ", grondoppervlakte='" + getGrondoppervlakte() + "'" +
            ", soortobjectcode='" + getSoortobjectcode() + "'" +
            ", statuswozobject='" + getStatuswozobject() + "'" +
            ", vastgesteldewaarde='" + getVastgesteldewaarde() + "'" +
            ", wozobjectnummer='" + getWozobjectnummer() + "'" +
            "}";
    }
}
