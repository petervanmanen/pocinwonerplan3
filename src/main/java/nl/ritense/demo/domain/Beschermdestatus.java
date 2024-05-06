package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Beschermdestatus.
 */
@Entity
@Table(name = "beschermdestatus")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beschermdestatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bronnen")
    private String bronnen;

    @Column(name = "complex")
    private String complex;

    @Column(name = "datuminschrijvingregister")
    private LocalDate datuminschrijvingregister;

    @Column(name = "gemeentelijkmonumentcode")
    private String gemeentelijkmonumentcode;

    @Size(max = 20)
    @Column(name = "gezichtscode", length = 20)
    private String gezichtscode;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "rijksmonumentcode")
    private String rijksmonumentcode;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beschermdestatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBronnen() {
        return this.bronnen;
    }

    public Beschermdestatus bronnen(String bronnen) {
        this.setBronnen(bronnen);
        return this;
    }

    public void setBronnen(String bronnen) {
        this.bronnen = bronnen;
    }

    public String getComplex() {
        return this.complex;
    }

    public Beschermdestatus complex(String complex) {
        this.setComplex(complex);
        return this;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public LocalDate getDatuminschrijvingregister() {
        return this.datuminschrijvingregister;
    }

    public Beschermdestatus datuminschrijvingregister(LocalDate datuminschrijvingregister) {
        this.setDatuminschrijvingregister(datuminschrijvingregister);
        return this;
    }

    public void setDatuminschrijvingregister(LocalDate datuminschrijvingregister) {
        this.datuminschrijvingregister = datuminschrijvingregister;
    }

    public String getGemeentelijkmonumentcode() {
        return this.gemeentelijkmonumentcode;
    }

    public Beschermdestatus gemeentelijkmonumentcode(String gemeentelijkmonumentcode) {
        this.setGemeentelijkmonumentcode(gemeentelijkmonumentcode);
        return this;
    }

    public void setGemeentelijkmonumentcode(String gemeentelijkmonumentcode) {
        this.gemeentelijkmonumentcode = gemeentelijkmonumentcode;
    }

    public String getGezichtscode() {
        return this.gezichtscode;
    }

    public Beschermdestatus gezichtscode(String gezichtscode) {
        this.setGezichtscode(gezichtscode);
        return this;
    }

    public void setGezichtscode(String gezichtscode) {
        this.gezichtscode = gezichtscode;
    }

    public String getNaam() {
        return this.naam;
    }

    public Beschermdestatus naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Beschermdestatus omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Beschermdestatus opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getRijksmonumentcode() {
        return this.rijksmonumentcode;
    }

    public Beschermdestatus rijksmonumentcode(String rijksmonumentcode) {
        this.setRijksmonumentcode(rijksmonumentcode);
        return this;
    }

    public void setRijksmonumentcode(String rijksmonumentcode) {
        this.rijksmonumentcode = rijksmonumentcode;
    }

    public String getType() {
        return this.type;
    }

    public Beschermdestatus type(String type) {
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
        if (!(o instanceof Beschermdestatus)) {
            return false;
        }
        return getId() != null && getId().equals(((Beschermdestatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beschermdestatus{" +
            "id=" + getId() +
            ", bronnen='" + getBronnen() + "'" +
            ", complex='" + getComplex() + "'" +
            ", datuminschrijvingregister='" + getDatuminschrijvingregister() + "'" +
            ", gemeentelijkmonumentcode='" + getGemeentelijkmonumentcode() + "'" +
            ", gezichtscode='" + getGezichtscode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", rijksmonumentcode='" + getRijksmonumentcode() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
