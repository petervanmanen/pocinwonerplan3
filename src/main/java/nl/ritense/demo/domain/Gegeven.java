package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Gegeven.
 */
@Entity
@Table(name = "gegeven")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gegeven implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "alias")
    private String alias;

    @Column(name = "eaguid")
    private String eaguid;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "stereotype")
    private String stereotype;

    @Column(name = "toelichting")
    private String toelichting;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_gegeven__geclassificeerdals_classificatie",
        joinColumns = @JoinColumn(name = "gegeven_id"),
        inverseJoinColumns = @JoinColumn(name = "geclassificeerdals_classificatie_id")
    )
    @JsonIgnoreProperties(value = { "geclassificeerdalsGegevens" }, allowSetters = true)
    private Set<Classificatie> geclassificeerdalsClassificaties = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftversiesVersies",
            "bevatGegevens",
            "heeftherkomstBatches",
            "heeftnotitiesNotities",
            "heeftleverancierLeverancier",
            "heeftdocumentenDocuments",
            "rollenMedewerkers",
        },
        allowSetters = true
    )
    private Applicatie bevatApplicatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getAlias() {
        return this.alias;
    }

    public Gegeven alias(String alias) {
        this.setAlias(alias);
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEaguid() {
        return this.eaguid;
    }

    public Gegeven eaguid(String eaguid) {
        this.setEaguid(eaguid);
        return this;
    }

    public void setEaguid(String eaguid) {
        this.eaguid = eaguid;
    }

    public String getId() {
        return this.id;
    }

    public Gegeven id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Gegeven naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getStereotype() {
        return this.stereotype;
    }

    public Gegeven stereotype(String stereotype) {
        this.setStereotype(stereotype);
        return this;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Gegeven toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public Set<Classificatie> getGeclassificeerdalsClassificaties() {
        return this.geclassificeerdalsClassificaties;
    }

    public void setGeclassificeerdalsClassificaties(Set<Classificatie> classificaties) {
        this.geclassificeerdalsClassificaties = classificaties;
    }

    public Gegeven geclassificeerdalsClassificaties(Set<Classificatie> classificaties) {
        this.setGeclassificeerdalsClassificaties(classificaties);
        return this;
    }

    public Gegeven addGeclassificeerdalsClassificatie(Classificatie classificatie) {
        this.geclassificeerdalsClassificaties.add(classificatie);
        return this;
    }

    public Gegeven removeGeclassificeerdalsClassificatie(Classificatie classificatie) {
        this.geclassificeerdalsClassificaties.remove(classificatie);
        return this;
    }

    public Applicatie getBevatApplicatie() {
        return this.bevatApplicatie;
    }

    public void setBevatApplicatie(Applicatie applicatie) {
        this.bevatApplicatie = applicatie;
    }

    public Gegeven bevatApplicatie(Applicatie applicatie) {
        this.setBevatApplicatie(applicatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gegeven)) {
            return false;
        }
        return getId() != null && getId().equals(((Gegeven) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gegeven{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", eaguid='" + getEaguid() + "'" +
            ", naam='" + getNaam() + "'" +
            ", stereotype='" + getStereotype() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
