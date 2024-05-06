package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Generalisatie.
 */
@Entity
@Table(name = "generalisatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Generalisatie implements Serializable {

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

    @Column(name = "indicatiematerielehistorie")
    private Boolean indicatiematerielehistorie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public LocalDate getDatumopname() {
        return this.datumopname;
    }

    public Generalisatie datumopname(LocalDate datumopname) {
        this.setDatumopname(datumopname);
        return this;
    }

    public void setDatumopname(LocalDate datumopname) {
        this.datumopname = datumopname;
    }

    public String getDefinitie() {
        return this.definitie;
    }

    public Generalisatie definitie(String definitie) {
        this.setDefinitie(definitie);
        return this;
    }

    public void setDefinitie(String definitie) {
        this.definitie = definitie;
    }

    public String getEaguid() {
        return this.eaguid;
    }

    public Generalisatie eaguid(String eaguid) {
        this.setEaguid(eaguid);
        return this;
    }

    public void setEaguid(String eaguid) {
        this.eaguid = eaguid;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Generalisatie herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getHerkomstdefinitie() {
        return this.herkomstdefinitie;
    }

    public Generalisatie herkomstdefinitie(String herkomstdefinitie) {
        this.setHerkomstdefinitie(herkomstdefinitie);
        return this;
    }

    public void setHerkomstdefinitie(String herkomstdefinitie) {
        this.herkomstdefinitie = herkomstdefinitie;
    }

    public String getId() {
        return this.id;
    }

    public Generalisatie id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIndicatiematerielehistorie() {
        return this.indicatiematerielehistorie;
    }

    public Generalisatie indicatiematerielehistorie(Boolean indicatiematerielehistorie) {
        this.setIndicatiematerielehistorie(indicatiematerielehistorie);
        return this;
    }

    public void setIndicatiematerielehistorie(Boolean indicatiematerielehistorie) {
        this.indicatiematerielehistorie = indicatiematerielehistorie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Generalisatie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Generalisatie toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Generalisatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Generalisatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Generalisatie{" +
            "id=" + getId() +
            ", datumopname='" + getDatumopname() + "'" +
            ", definitie='" + getDefinitie() + "'" +
            ", eaguid='" + getEaguid() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", herkomstdefinitie='" + getHerkomstdefinitie() + "'" +
            ", indicatiematerielehistorie='" + getIndicatiematerielehistorie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
