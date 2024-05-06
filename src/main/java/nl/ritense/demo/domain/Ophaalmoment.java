package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Ophaalmoment.
 */
@Entity
@Table(name = "ophaalmoment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ophaalmoment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gewichtstoename")
    private String gewichtstoename;

    @Column(name = "tijdstip")
    private String tijdstip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftVulgraadmetings", "geschiktvoorFractie", "soortContainertype", "heeftLocatie", "gelostOphaalmoments" },
        allowSetters = true
    )
    private Container gelostContainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Locatie gestoptopLocatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftOphaalmoments", "uitgevoerdmetVuilniswagen" }, allowSetters = true)
    private Rit heeftRit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ophaalmoment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGewichtstoename() {
        return this.gewichtstoename;
    }

    public Ophaalmoment gewichtstoename(String gewichtstoename) {
        this.setGewichtstoename(gewichtstoename);
        return this;
    }

    public void setGewichtstoename(String gewichtstoename) {
        this.gewichtstoename = gewichtstoename;
    }

    public String getTijdstip() {
        return this.tijdstip;
    }

    public Ophaalmoment tijdstip(String tijdstip) {
        this.setTijdstip(tijdstip);
        return this;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public Container getGelostContainer() {
        return this.gelostContainer;
    }

    public void setGelostContainer(Container container) {
        this.gelostContainer = container;
    }

    public Ophaalmoment gelostContainer(Container container) {
        this.setGelostContainer(container);
        return this;
    }

    public Locatie getGestoptopLocatie() {
        return this.gestoptopLocatie;
    }

    public void setGestoptopLocatie(Locatie locatie) {
        this.gestoptopLocatie = locatie;
    }

    public Ophaalmoment gestoptopLocatie(Locatie locatie) {
        this.setGestoptopLocatie(locatie);
        return this;
    }

    public Rit getHeeftRit() {
        return this.heeftRit;
    }

    public void setHeeftRit(Rit rit) {
        this.heeftRit = rit;
    }

    public Ophaalmoment heeftRit(Rit rit) {
        this.setHeeftRit(rit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ophaalmoment)) {
            return false;
        }
        return getId() != null && getId().equals(((Ophaalmoment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ophaalmoment{" +
            "id=" + getId() +
            ", gewichtstoename='" + getGewichtstoename() + "'" +
            ", tijdstip='" + getTijdstip() + "'" +
            "}";
    }
}
