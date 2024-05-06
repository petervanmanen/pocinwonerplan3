package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Specificatie.
 */
@Entity
@Table(name = "specificatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Specificatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "antwoord")
    private String antwoord;

    @Column(name = "groepering")
    private String groepering;

    @Column(name = "publiceerbaar")
    private String publiceerbaar;

    @Column(name = "vraagclassificatie")
    private String vraagclassificatie;

    @Column(name = "vraagid")
    private String vraagid;

    @Column(name = "vraagreferentie")
    private String vraagreferentie;

    @Column(name = "vraagtekst")
    private String vraagtekst;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "heeftProject", "gedefinieerddoorSpecificaties", "betreftVerzoeks" }, allowSetters = true)
    private Projectactiviteit gedefinieerddoorProjectactiviteit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    private Verzoek bevatVerzoek;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Specificatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntwoord() {
        return this.antwoord;
    }

    public Specificatie antwoord(String antwoord) {
        this.setAntwoord(antwoord);
        return this;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public String getGroepering() {
        return this.groepering;
    }

    public Specificatie groepering(String groepering) {
        this.setGroepering(groepering);
        return this;
    }

    public void setGroepering(String groepering) {
        this.groepering = groepering;
    }

    public String getPubliceerbaar() {
        return this.publiceerbaar;
    }

    public Specificatie publiceerbaar(String publiceerbaar) {
        this.setPubliceerbaar(publiceerbaar);
        return this;
    }

    public void setPubliceerbaar(String publiceerbaar) {
        this.publiceerbaar = publiceerbaar;
    }

    public String getVraagclassificatie() {
        return this.vraagclassificatie;
    }

    public Specificatie vraagclassificatie(String vraagclassificatie) {
        this.setVraagclassificatie(vraagclassificatie);
        return this;
    }

    public void setVraagclassificatie(String vraagclassificatie) {
        this.vraagclassificatie = vraagclassificatie;
    }

    public String getVraagid() {
        return this.vraagid;
    }

    public Specificatie vraagid(String vraagid) {
        this.setVraagid(vraagid);
        return this;
    }

    public void setVraagid(String vraagid) {
        this.vraagid = vraagid;
    }

    public String getVraagreferentie() {
        return this.vraagreferentie;
    }

    public Specificatie vraagreferentie(String vraagreferentie) {
        this.setVraagreferentie(vraagreferentie);
        return this;
    }

    public void setVraagreferentie(String vraagreferentie) {
        this.vraagreferentie = vraagreferentie;
    }

    public String getVraagtekst() {
        return this.vraagtekst;
    }

    public Specificatie vraagtekst(String vraagtekst) {
        this.setVraagtekst(vraagtekst);
        return this;
    }

    public void setVraagtekst(String vraagtekst) {
        this.vraagtekst = vraagtekst;
    }

    public Projectactiviteit getGedefinieerddoorProjectactiviteit() {
        return this.gedefinieerddoorProjectactiviteit;
    }

    public void setGedefinieerddoorProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.gedefinieerddoorProjectactiviteit = projectactiviteit;
    }

    public Specificatie gedefinieerddoorProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.setGedefinieerddoorProjectactiviteit(projectactiviteit);
        return this;
    }

    public Verzoek getBevatVerzoek() {
        return this.bevatVerzoek;
    }

    public void setBevatVerzoek(Verzoek verzoek) {
        this.bevatVerzoek = verzoek;
    }

    public Specificatie bevatVerzoek(Verzoek verzoek) {
        this.setBevatVerzoek(verzoek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specificatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Specificatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specificatie{" +
            "id=" + getId() +
            ", antwoord='" + getAntwoord() + "'" +
            ", groepering='" + getGroepering() + "'" +
            ", publiceerbaar='" + getPubliceerbaar() + "'" +
            ", vraagclassificatie='" + getVraagclassificatie() + "'" +
            ", vraagid='" + getVraagid() + "'" +
            ", vraagreferentie='" + getVraagreferentie() + "'" +
            ", vraagtekst='" + getVraagtekst() + "'" +
            "}";
    }
}
