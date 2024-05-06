package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Nationaliteitingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "nationaliteitingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nationaliteitingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandspersoonsnummer")
    private String buitenlandspersoonsnummer;

    @Column(name = "nationaliteit")
    private String nationaliteit;

    @Column(name = "redenverkrijging")
    private String redenverkrijging;

    @Column(name = "redenverlies")
    private String redenverlies;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nationaliteitingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandspersoonsnummer() {
        return this.buitenlandspersoonsnummer;
    }

    public Nationaliteitingeschrevennatuurlijkpersoon buitenlandspersoonsnummer(String buitenlandspersoonsnummer) {
        this.setBuitenlandspersoonsnummer(buitenlandspersoonsnummer);
        return this;
    }

    public void setBuitenlandspersoonsnummer(String buitenlandspersoonsnummer) {
        this.buitenlandspersoonsnummer = buitenlandspersoonsnummer;
    }

    public String getNationaliteit() {
        return this.nationaliteit;
    }

    public Nationaliteitingeschrevennatuurlijkpersoon nationaliteit(String nationaliteit) {
        this.setNationaliteit(nationaliteit);
        return this;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getRedenverkrijging() {
        return this.redenverkrijging;
    }

    public Nationaliteitingeschrevennatuurlijkpersoon redenverkrijging(String redenverkrijging) {
        this.setRedenverkrijging(redenverkrijging);
        return this;
    }

    public void setRedenverkrijging(String redenverkrijging) {
        this.redenverkrijging = redenverkrijging;
    }

    public String getRedenverlies() {
        return this.redenverlies;
    }

    public Nationaliteitingeschrevennatuurlijkpersoon redenverlies(String redenverlies) {
        this.setRedenverlies(redenverlies);
        return this;
    }

    public void setRedenverlies(String redenverlies) {
        this.redenverlies = redenverlies;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nationaliteitingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Nationaliteitingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nationaliteitingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", buitenlandspersoonsnummer='" + getBuitenlandspersoonsnummer() + "'" +
            ", nationaliteit='" + getNationaliteit() + "'" +
            ", redenverkrijging='" + getRedenverkrijging() + "'" +
            ", redenverlies='" + getRedenverlies() + "'" +
            "}";
    }
}
