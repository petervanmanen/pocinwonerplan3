package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Eobjecttype.
 */
@Entity
@Table(name = "eobjecttype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Eobjecttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "datumopname")
    private LocalDate datumopname;

    @Column(name = "definitie")
    private String definitie;

    @Column(name = "eaguid")
    private String eaguid;

    @Column(name = "herkomst")
    private String herkomst;

    @Column(name = "herkomstdefinitie")
    private String herkomstdefinitie;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "indicatieabstract")
    private Boolean indicatieabstract;

    @Column(name = "kwaliteit")
    private String kwaliteit;

    @Column(name = "naam")
    private String naam;

    @Column(name = "populatie")
    private String populatie;

    @Column(name = "stereotype")
    private String stereotype;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "uniekeaanduiding")
    private String uniekeaanduiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public LocalDate getDatumopname() {
        return this.datumopname;
    }

    public Eobjecttype datumopname(LocalDate datumopname) {
        this.setDatumopname(datumopname);
        return this;
    }

    public void setDatumopname(LocalDate datumopname) {
        this.datumopname = datumopname;
    }

    public String getDefinitie() {
        return this.definitie;
    }

    public Eobjecttype definitie(String definitie) {
        this.setDefinitie(definitie);
        return this;
    }

    public void setDefinitie(String definitie) {
        this.definitie = definitie;
    }

    public String getEaguid() {
        return this.eaguid;
    }

    public Eobjecttype eaguid(String eaguid) {
        this.setEaguid(eaguid);
        return this;
    }

    public void setEaguid(String eaguid) {
        this.eaguid = eaguid;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Eobjecttype herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getHerkomstdefinitie() {
        return this.herkomstdefinitie;
    }

    public Eobjecttype herkomstdefinitie(String herkomstdefinitie) {
        this.setHerkomstdefinitie(herkomstdefinitie);
        return this;
    }

    public void setHerkomstdefinitie(String herkomstdefinitie) {
        this.herkomstdefinitie = herkomstdefinitie;
    }

    public String getId() {
        return this.id;
    }

    public Eobjecttype id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIndicatieabstract() {
        return this.indicatieabstract;
    }

    public Eobjecttype indicatieabstract(Boolean indicatieabstract) {
        this.setIndicatieabstract(indicatieabstract);
        return this;
    }

    public void setIndicatieabstract(Boolean indicatieabstract) {
        this.indicatieabstract = indicatieabstract;
    }

    public String getKwaliteit() {
        return this.kwaliteit;
    }

    public Eobjecttype kwaliteit(String kwaliteit) {
        this.setKwaliteit(kwaliteit);
        return this;
    }

    public void setKwaliteit(String kwaliteit) {
        this.kwaliteit = kwaliteit;
    }

    public String getNaam() {
        return this.naam;
    }

    public Eobjecttype naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPopulatie() {
        return this.populatie;
    }

    public Eobjecttype populatie(String populatie) {
        this.setPopulatie(populatie);
        return this;
    }

    public void setPopulatie(String populatie) {
        this.populatie = populatie;
    }

    public String getStereotype() {
        return this.stereotype;
    }

    public Eobjecttype stereotype(String stereotype) {
        this.setStereotype(stereotype);
        return this;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Eobjecttype toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getUniekeaanduiding() {
        return this.uniekeaanduiding;
    }

    public Eobjecttype uniekeaanduiding(String uniekeaanduiding) {
        this.setUniekeaanduiding(uniekeaanduiding);
        return this;
    }

    public void setUniekeaanduiding(String uniekeaanduiding) {
        this.uniekeaanduiding = uniekeaanduiding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eobjecttype)) {
            return false;
        }
        return getId() != null && getId().equals(((Eobjecttype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eobjecttype{" +
            "id=" + getId() +
            ", datumopname='" + getDatumopname() + "'" +
            ", definitie='" + getDefinitie() + "'" +
            ", eaguid='" + getEaguid() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", herkomstdefinitie='" + getHerkomstdefinitie() + "'" +
            ", indicatieabstract='" + getIndicatieabstract() + "'" +
            ", kwaliteit='" + getKwaliteit() + "'" +
            ", naam='" + getNaam() + "'" +
            ", populatie='" + getPopulatie() + "'" +
            ", stereotype='" + getStereotype() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", uniekeaanduiding='" + getUniekeaanduiding() + "'" +
            "}";
    }
}
