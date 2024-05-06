package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Opleiding.
 */
@Entity
@Table(name = "opleiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opleiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "instituut")
    private String instituut;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "prijs")
    private String prijs;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_opleiding__wordtgegevendoor_onderwijsinstituut",
        joinColumns = @JoinColumn(name = "opleiding_id"),
        inverseJoinColumns = @JoinColumn(name = "wordtgegevendoor_onderwijsinstituut_id")
    )
    @JsonIgnoreProperties(value = { "wordtgegevendoorOpleidings" }, allowSetters = true)
    private Set<Onderwijsinstituut> wordtgegevendoorOnderwijsinstituuts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opleiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituut() {
        return this.instituut;
    }

    public Opleiding instituut(String instituut) {
        this.setInstituut(instituut);
        return this;
    }

    public void setInstituut(String instituut) {
        this.instituut = instituut;
    }

    public String getNaam() {
        return this.naam;
    }

    public Opleiding naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Opleiding omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPrijs() {
        return this.prijs;
    }

    public Opleiding prijs(String prijs) {
        this.setPrijs(prijs);
        return this;
    }

    public void setPrijs(String prijs) {
        this.prijs = prijs;
    }

    public Set<Onderwijsinstituut> getWordtgegevendoorOnderwijsinstituuts() {
        return this.wordtgegevendoorOnderwijsinstituuts;
    }

    public void setWordtgegevendoorOnderwijsinstituuts(Set<Onderwijsinstituut> onderwijsinstituuts) {
        this.wordtgegevendoorOnderwijsinstituuts = onderwijsinstituuts;
    }

    public Opleiding wordtgegevendoorOnderwijsinstituuts(Set<Onderwijsinstituut> onderwijsinstituuts) {
        this.setWordtgegevendoorOnderwijsinstituuts(onderwijsinstituuts);
        return this;
    }

    public Opleiding addWordtgegevendoorOnderwijsinstituut(Onderwijsinstituut onderwijsinstituut) {
        this.wordtgegevendoorOnderwijsinstituuts.add(onderwijsinstituut);
        return this;
    }

    public Opleiding removeWordtgegevendoorOnderwijsinstituut(Onderwijsinstituut onderwijsinstituut) {
        this.wordtgegevendoorOnderwijsinstituuts.remove(onderwijsinstituut);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opleiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Opleiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opleiding{" +
            "id=" + getId() +
            ", instituut='" + getInstituut() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", prijs='" + getPrijs() + "'" +
            "}";
    }
}
