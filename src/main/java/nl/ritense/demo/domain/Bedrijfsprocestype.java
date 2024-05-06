package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bedrijfsprocestype.
 */
@Entity
@Table(name = "bedrijfsprocestype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bedrijfsprocestype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBedrijfsprocestype")
    @JsonIgnoreProperties(value = { "heeftproductZaak", "heeftZaaktype", "heeftBedrijfsprocestype" }, allowSetters = true)
    private Set<Producttype> heeftProducttypes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBedrijfsprocestype")
    @JsonIgnoreProperties(
        value = {
            "heeftProducttype",
            "heeftHeffinggrondslags",
            "heeftStatustypes",
            "betreftProduct",
            "heeftBedrijfsprocestype",
            "isverantwoordelijkevoorMedewerker",
            "startDiensts",
            "isvanZaaks",
            "isaanleidingvoorFormuliersoorts",
        },
        allowSetters = true
    )
    private Set<Zaaktype> heeftZaaktypes = new HashSet<>();

    @JsonIgnoreProperties(value = { "isdeelvanBedrijfsprocestype", "isvanDeelproces" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isdeelvanBedrijfsprocestype")
    private Deelprocestype isdeelvanDeelprocestype;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bedrijfsprocestype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bedrijfsprocestype omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Producttype> getHeeftProducttypes() {
        return this.heeftProducttypes;
    }

    public void setHeeftProducttypes(Set<Producttype> producttypes) {
        if (this.heeftProducttypes != null) {
            this.heeftProducttypes.forEach(i -> i.setHeeftBedrijfsprocestype(null));
        }
        if (producttypes != null) {
            producttypes.forEach(i -> i.setHeeftBedrijfsprocestype(this));
        }
        this.heeftProducttypes = producttypes;
    }

    public Bedrijfsprocestype heeftProducttypes(Set<Producttype> producttypes) {
        this.setHeeftProducttypes(producttypes);
        return this;
    }

    public Bedrijfsprocestype addHeeftProducttype(Producttype producttype) {
        this.heeftProducttypes.add(producttype);
        producttype.setHeeftBedrijfsprocestype(this);
        return this;
    }

    public Bedrijfsprocestype removeHeeftProducttype(Producttype producttype) {
        this.heeftProducttypes.remove(producttype);
        producttype.setHeeftBedrijfsprocestype(null);
        return this;
    }

    public Set<Zaaktype> getHeeftZaaktypes() {
        return this.heeftZaaktypes;
    }

    public void setHeeftZaaktypes(Set<Zaaktype> zaaktypes) {
        if (this.heeftZaaktypes != null) {
            this.heeftZaaktypes.forEach(i -> i.setHeeftBedrijfsprocestype(null));
        }
        if (zaaktypes != null) {
            zaaktypes.forEach(i -> i.setHeeftBedrijfsprocestype(this));
        }
        this.heeftZaaktypes = zaaktypes;
    }

    public Bedrijfsprocestype heeftZaaktypes(Set<Zaaktype> zaaktypes) {
        this.setHeeftZaaktypes(zaaktypes);
        return this;
    }

    public Bedrijfsprocestype addHeeftZaaktype(Zaaktype zaaktype) {
        this.heeftZaaktypes.add(zaaktype);
        zaaktype.setHeeftBedrijfsprocestype(this);
        return this;
    }

    public Bedrijfsprocestype removeHeeftZaaktype(Zaaktype zaaktype) {
        this.heeftZaaktypes.remove(zaaktype);
        zaaktype.setHeeftBedrijfsprocestype(null);
        return this;
    }

    public Deelprocestype getIsdeelvanDeelprocestype() {
        return this.isdeelvanDeelprocestype;
    }

    public void setIsdeelvanDeelprocestype(Deelprocestype deelprocestype) {
        if (this.isdeelvanDeelprocestype != null) {
            this.isdeelvanDeelprocestype.setIsdeelvanBedrijfsprocestype(null);
        }
        if (deelprocestype != null) {
            deelprocestype.setIsdeelvanBedrijfsprocestype(this);
        }
        this.isdeelvanDeelprocestype = deelprocestype;
    }

    public Bedrijfsprocestype isdeelvanDeelprocestype(Deelprocestype deelprocestype) {
        this.setIsdeelvanDeelprocestype(deelprocestype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bedrijfsprocestype)) {
            return false;
        }
        return getId() != null && getId().equals(((Bedrijfsprocestype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bedrijfsprocestype{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
