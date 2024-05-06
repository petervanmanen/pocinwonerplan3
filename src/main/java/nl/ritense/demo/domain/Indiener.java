package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Indiener.
 */
@Entity
@Table(name = "indiener")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Indiener implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @JsonIgnoreProperties(value = { "isIndiener" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Collegelid isCollegelid;

    @JsonIgnoreProperties(value = { "islidvanRaadscommissies", "isIndiener" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Raadslid isRaadslid;

    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Rechtspersoon isRechtspersoon;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_indiener__heeft_raadsstuk",
        joinColumns = @JoinColumn(name = "indiener_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_raadsstuk_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftTaakveld",
            "behandeltAgendapunts",
            "hoortbijProgrammas",
            "wordtbehandeldinVergaderings",
            "heeftverslagVergadering",
            "betreftStemming",
            "heeftCategorie",
            "hoortbijDossiers",
            "heeftIndieners",
        },
        allowSetters = true
    )
    private Set<Raadsstuk> heeftRaadsstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Indiener id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Indiener naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Indiener omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Collegelid getIsCollegelid() {
        return this.isCollegelid;
    }

    public void setIsCollegelid(Collegelid collegelid) {
        this.isCollegelid = collegelid;
    }

    public Indiener isCollegelid(Collegelid collegelid) {
        this.setIsCollegelid(collegelid);
        return this;
    }

    public Raadslid getIsRaadslid() {
        return this.isRaadslid;
    }

    public void setIsRaadslid(Raadslid raadslid) {
        this.isRaadslid = raadslid;
    }

    public Indiener isRaadslid(Raadslid raadslid) {
        this.setIsRaadslid(raadslid);
        return this;
    }

    public Rechtspersoon getIsRechtspersoon() {
        return this.isRechtspersoon;
    }

    public void setIsRechtspersoon(Rechtspersoon rechtspersoon) {
        this.isRechtspersoon = rechtspersoon;
    }

    public Indiener isRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setIsRechtspersoon(rechtspersoon);
        return this;
    }

    public Set<Raadsstuk> getHeeftRaadsstuks() {
        return this.heeftRaadsstuks;
    }

    public void setHeeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.heeftRaadsstuks = raadsstuks;
    }

    public Indiener heeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setHeeftRaadsstuks(raadsstuks);
        return this;
    }

    public Indiener addHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.add(raadsstuk);
        return this;
    }

    public Indiener removeHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.remove(raadsstuk);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Indiener)) {
            return false;
        }
        return getId() != null && getId().equals(((Indiener) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Indiener{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
