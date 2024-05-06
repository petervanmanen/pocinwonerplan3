package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Formuliersoort.
 */
@Entity
@Table(name = "formuliersoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formuliersoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ingebruik")
    private String ingebruik;

    @Column(name = "naam")
    private String naam;

    @Column(name = "onderwerp")
    private String onderwerp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftveldenFormuliersoort")
    @JsonIgnoreProperties(value = { "heeftveldenFormuliersoort", "isconformAanvraagdata" }, allowSetters = true)
    private Set<Formuliersoortveld> heeftveldenFormuliersoortvelds = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_formuliersoort__isaanleidingvoor_zaaktype",
        joinColumns = @JoinColumn(name = "formuliersoort_id"),
        inverseJoinColumns = @JoinColumn(name = "isaanleidingvoor_zaaktype_id")
    )
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
    private Set<Zaaktype> isaanleidingvoorZaaktypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formuliersoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngebruik() {
        return this.ingebruik;
    }

    public Formuliersoort ingebruik(String ingebruik) {
        this.setIngebruik(ingebruik);
        return this;
    }

    public void setIngebruik(String ingebruik) {
        this.ingebruik = ingebruik;
    }

    public String getNaam() {
        return this.naam;
    }

    public Formuliersoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOnderwerp() {
        return this.onderwerp;
    }

    public Formuliersoort onderwerp(String onderwerp) {
        this.setOnderwerp(onderwerp);
        return this;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public Set<Formuliersoortveld> getHeeftveldenFormuliersoortvelds() {
        return this.heeftveldenFormuliersoortvelds;
    }

    public void setHeeftveldenFormuliersoortvelds(Set<Formuliersoortveld> formuliersoortvelds) {
        if (this.heeftveldenFormuliersoortvelds != null) {
            this.heeftveldenFormuliersoortvelds.forEach(i -> i.setHeeftveldenFormuliersoort(null));
        }
        if (formuliersoortvelds != null) {
            formuliersoortvelds.forEach(i -> i.setHeeftveldenFormuliersoort(this));
        }
        this.heeftveldenFormuliersoortvelds = formuliersoortvelds;
    }

    public Formuliersoort heeftveldenFormuliersoortvelds(Set<Formuliersoortveld> formuliersoortvelds) {
        this.setHeeftveldenFormuliersoortvelds(formuliersoortvelds);
        return this;
    }

    public Formuliersoort addHeeftveldenFormuliersoortveld(Formuliersoortveld formuliersoortveld) {
        this.heeftveldenFormuliersoortvelds.add(formuliersoortveld);
        formuliersoortveld.setHeeftveldenFormuliersoort(this);
        return this;
    }

    public Formuliersoort removeHeeftveldenFormuliersoortveld(Formuliersoortveld formuliersoortveld) {
        this.heeftveldenFormuliersoortvelds.remove(formuliersoortveld);
        formuliersoortveld.setHeeftveldenFormuliersoort(null);
        return this;
    }

    public Set<Zaaktype> getIsaanleidingvoorZaaktypes() {
        return this.isaanleidingvoorZaaktypes;
    }

    public void setIsaanleidingvoorZaaktypes(Set<Zaaktype> zaaktypes) {
        this.isaanleidingvoorZaaktypes = zaaktypes;
    }

    public Formuliersoort isaanleidingvoorZaaktypes(Set<Zaaktype> zaaktypes) {
        this.setIsaanleidingvoorZaaktypes(zaaktypes);
        return this;
    }

    public Formuliersoort addIsaanleidingvoorZaaktype(Zaaktype zaaktype) {
        this.isaanleidingvoorZaaktypes.add(zaaktype);
        return this;
    }

    public Formuliersoort removeIsaanleidingvoorZaaktype(Zaaktype zaaktype) {
        this.isaanleidingvoorZaaktypes.remove(zaaktype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formuliersoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Formuliersoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formuliersoort{" +
            "id=" + getId() +
            ", ingebruik='" + getIngebruik() + "'" +
            ", naam='" + getNaam() + "'" +
            ", onderwerp='" + getOnderwerp() + "'" +
            "}";
    }
}
