package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Ontbindinghuwelijkgeregistreerdpartnerschap.
 */
@Entity
@Table(name = "ontbindinghuwelijkgeregistreerdpartnerschap")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ontbindinghuwelijkgeregistreerdpartnerschap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandseplaatseinde")
    private String buitenlandseplaatseinde;

    @Column(name = "buitenlandseregioeinde")
    private String buitenlandseregioeinde;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "gemeenteeinde")
    private String gemeenteeinde;

    @Column(name = "landofgebiedeinde")
    private String landofgebiedeinde;

    @Column(name = "omschrijvinglocatieeinde")
    private String omschrijvinglocatieeinde;

    @Column(name = "redeneinde")
    private String redeneinde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandseplaatseinde() {
        return this.buitenlandseplaatseinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap buitenlandseplaatseinde(String buitenlandseplaatseinde) {
        this.setBuitenlandseplaatseinde(buitenlandseplaatseinde);
        return this;
    }

    public void setBuitenlandseplaatseinde(String buitenlandseplaatseinde) {
        this.buitenlandseplaatseinde = buitenlandseplaatseinde;
    }

    public String getBuitenlandseregioeinde() {
        return this.buitenlandseregioeinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap buitenlandseregioeinde(String buitenlandseregioeinde) {
        this.setBuitenlandseregioeinde(buitenlandseregioeinde);
        return this;
    }

    public void setBuitenlandseregioeinde(String buitenlandseregioeinde) {
        this.buitenlandseregioeinde = buitenlandseregioeinde;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getGemeenteeinde() {
        return this.gemeenteeinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap gemeenteeinde(String gemeenteeinde) {
        this.setGemeenteeinde(gemeenteeinde);
        return this;
    }

    public void setGemeenteeinde(String gemeenteeinde) {
        this.gemeenteeinde = gemeenteeinde;
    }

    public String getLandofgebiedeinde() {
        return this.landofgebiedeinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap landofgebiedeinde(String landofgebiedeinde) {
        this.setLandofgebiedeinde(landofgebiedeinde);
        return this;
    }

    public void setLandofgebiedeinde(String landofgebiedeinde) {
        this.landofgebiedeinde = landofgebiedeinde;
    }

    public String getOmschrijvinglocatieeinde() {
        return this.omschrijvinglocatieeinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap omschrijvinglocatieeinde(String omschrijvinglocatieeinde) {
        this.setOmschrijvinglocatieeinde(omschrijvinglocatieeinde);
        return this;
    }

    public void setOmschrijvinglocatieeinde(String omschrijvinglocatieeinde) {
        this.omschrijvinglocatieeinde = omschrijvinglocatieeinde;
    }

    public String getRedeneinde() {
        return this.redeneinde;
    }

    public Ontbindinghuwelijkgeregistreerdpartnerschap redeneinde(String redeneinde) {
        this.setRedeneinde(redeneinde);
        return this;
    }

    public void setRedeneinde(String redeneinde) {
        this.redeneinde = redeneinde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ontbindinghuwelijkgeregistreerdpartnerschap)) {
            return false;
        }
        return getId() != null && getId().equals(((Ontbindinghuwelijkgeregistreerdpartnerschap) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ontbindinghuwelijkgeregistreerdpartnerschap{" +
            "id=" + getId() +
            ", buitenlandseplaatseinde='" + getBuitenlandseplaatseinde() + "'" +
            ", buitenlandseregioeinde='" + getBuitenlandseregioeinde() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", gemeenteeinde='" + getGemeenteeinde() + "'" +
            ", landofgebiedeinde='" + getLandofgebiedeinde() + "'" +
            ", omschrijvinglocatieeinde='" + getOmschrijvinglocatieeinde() + "'" +
            ", redeneinde='" + getRedeneinde() + "'" +
            "}";
    }
}
