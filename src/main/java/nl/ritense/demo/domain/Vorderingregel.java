package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vorderingregel.
 */
@Entity
@Table(name = "vorderingregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vorderingregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Column(name = "aanmaakdatum")
    private LocalDate aanmaakdatum;

    @Column(name = "bedragexclbtw")
    private String bedragexclbtw;

    @Column(name = "bedraginclbtw")
    private String bedraginclbtw;

    @Column(name = "btwcategorie")
    private String btwcategorie;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Column(name = "mutatiedatum")
    private LocalDate mutatiedatum;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "periodiek")
    private String periodiek;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vorderingregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Vorderingregel aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public LocalDate getAanmaakdatum() {
        return this.aanmaakdatum;
    }

    public Vorderingregel aanmaakdatum(LocalDate aanmaakdatum) {
        this.setAanmaakdatum(aanmaakdatum);
        return this;
    }

    public void setAanmaakdatum(LocalDate aanmaakdatum) {
        this.aanmaakdatum = aanmaakdatum;
    }

    public String getBedragexclbtw() {
        return this.bedragexclbtw;
    }

    public Vorderingregel bedragexclbtw(String bedragexclbtw) {
        this.setBedragexclbtw(bedragexclbtw);
        return this;
    }

    public void setBedragexclbtw(String bedragexclbtw) {
        this.bedragexclbtw = bedragexclbtw;
    }

    public String getBedraginclbtw() {
        return this.bedraginclbtw;
    }

    public Vorderingregel bedraginclbtw(String bedraginclbtw) {
        this.setBedraginclbtw(bedraginclbtw);
        return this;
    }

    public void setBedraginclbtw(String bedraginclbtw) {
        this.bedraginclbtw = bedraginclbtw;
    }

    public String getBtwcategorie() {
        return this.btwcategorie;
    }

    public Vorderingregel btwcategorie(String btwcategorie) {
        this.setBtwcategorie(btwcategorie);
        return this;
    }

    public void setBtwcategorie(String btwcategorie) {
        this.btwcategorie = btwcategorie;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Vorderingregel gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public LocalDate getMutatiedatum() {
        return this.mutatiedatum;
    }

    public Vorderingregel mutatiedatum(LocalDate mutatiedatum) {
        this.setMutatiedatum(mutatiedatum);
        return this;
    }

    public void setMutatiedatum(LocalDate mutatiedatum) {
        this.mutatiedatum = mutatiedatum;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Vorderingregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPeriodiek() {
        return this.periodiek;
    }

    public Vorderingregel periodiek(String periodiek) {
        this.setPeriodiek(periodiek);
        return this;
    }

    public void setPeriodiek(String periodiek) {
        this.periodiek = periodiek;
    }

    public String getType() {
        return this.type;
    }

    public Vorderingregel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vorderingregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Vorderingregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vorderingregel{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", aanmaakdatum='" + getAanmaakdatum() + "'" +
            ", bedragexclbtw='" + getBedragexclbtw() + "'" +
            ", bedraginclbtw='" + getBedraginclbtw() + "'" +
            ", btwcategorie='" + getBtwcategorie() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", mutatiedatum='" + getMutatiedatum() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", periodiek='" + getPeriodiek() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
