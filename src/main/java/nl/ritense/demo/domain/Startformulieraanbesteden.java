package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Startformulieraanbesteden.
 */
@Entity
@Table(name = "startformulieraanbesteden")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Startformulieraanbesteden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beoogdelooptijd")
    private String beoogdelooptijd;

    @Column(name = "beoogdetotaleopdrachtwaarde", precision = 21, scale = 2)
    private BigDecimal beoogdetotaleopdrachtwaarde;

    @Column(name = "indicatieaanvullendeopdrachtleverancier")
    private Boolean indicatieaanvullendeopdrachtleverancier;

    @Column(name = "indicatiebeoogdeaanbestedingonderhands")
    private String indicatiebeoogdeaanbestedingonderhands;

    @Column(name = "indicatiebeoogdeprockomtovereen")
    private Boolean indicatiebeoogdeprockomtovereen;

    @Column(name = "indicatieeenmaligelos")
    private String indicatieeenmaligelos;

    @Column(name = "indicatiemeerjarigeraamovereenkomst")
    private Boolean indicatiemeerjarigeraamovereenkomst;

    @Column(name = "indicatiemeerjarigrepeterend")
    private String indicatiemeerjarigrepeterend;

    @Column(name = "indicatoroverkoepelendproject")
    private String indicatoroverkoepelendproject;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "opdrachtcategorie")
    private String opdrachtcategorie;

    @Column(name = "opdrachtsoort")
    private String opdrachtsoort;

    @Column(name = "toelichtingaanvullendeopdracht")
    private String toelichtingaanvullendeopdracht;

    @Column(name = "toelichtingeenmaligofrepeterend")
    private String toelichtingeenmaligofrepeterend;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Startformulieraanbesteden id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeoogdelooptijd() {
        return this.beoogdelooptijd;
    }

    public Startformulieraanbesteden beoogdelooptijd(String beoogdelooptijd) {
        this.setBeoogdelooptijd(beoogdelooptijd);
        return this;
    }

    public void setBeoogdelooptijd(String beoogdelooptijd) {
        this.beoogdelooptijd = beoogdelooptijd;
    }

    public BigDecimal getBeoogdetotaleopdrachtwaarde() {
        return this.beoogdetotaleopdrachtwaarde;
    }

    public Startformulieraanbesteden beoogdetotaleopdrachtwaarde(BigDecimal beoogdetotaleopdrachtwaarde) {
        this.setBeoogdetotaleopdrachtwaarde(beoogdetotaleopdrachtwaarde);
        return this;
    }

    public void setBeoogdetotaleopdrachtwaarde(BigDecimal beoogdetotaleopdrachtwaarde) {
        this.beoogdetotaleopdrachtwaarde = beoogdetotaleopdrachtwaarde;
    }

    public Boolean getIndicatieaanvullendeopdrachtleverancier() {
        return this.indicatieaanvullendeopdrachtleverancier;
    }

    public Startformulieraanbesteden indicatieaanvullendeopdrachtleverancier(Boolean indicatieaanvullendeopdrachtleverancier) {
        this.setIndicatieaanvullendeopdrachtleverancier(indicatieaanvullendeopdrachtleverancier);
        return this;
    }

    public void setIndicatieaanvullendeopdrachtleverancier(Boolean indicatieaanvullendeopdrachtleverancier) {
        this.indicatieaanvullendeopdrachtleverancier = indicatieaanvullendeopdrachtleverancier;
    }

    public String getIndicatiebeoogdeaanbestedingonderhands() {
        return this.indicatiebeoogdeaanbestedingonderhands;
    }

    public Startformulieraanbesteden indicatiebeoogdeaanbestedingonderhands(String indicatiebeoogdeaanbestedingonderhands) {
        this.setIndicatiebeoogdeaanbestedingonderhands(indicatiebeoogdeaanbestedingonderhands);
        return this;
    }

    public void setIndicatiebeoogdeaanbestedingonderhands(String indicatiebeoogdeaanbestedingonderhands) {
        this.indicatiebeoogdeaanbestedingonderhands = indicatiebeoogdeaanbestedingonderhands;
    }

    public Boolean getIndicatiebeoogdeprockomtovereen() {
        return this.indicatiebeoogdeprockomtovereen;
    }

    public Startformulieraanbesteden indicatiebeoogdeprockomtovereen(Boolean indicatiebeoogdeprockomtovereen) {
        this.setIndicatiebeoogdeprockomtovereen(indicatiebeoogdeprockomtovereen);
        return this;
    }

    public void setIndicatiebeoogdeprockomtovereen(Boolean indicatiebeoogdeprockomtovereen) {
        this.indicatiebeoogdeprockomtovereen = indicatiebeoogdeprockomtovereen;
    }

    public String getIndicatieeenmaligelos() {
        return this.indicatieeenmaligelos;
    }

    public Startformulieraanbesteden indicatieeenmaligelos(String indicatieeenmaligelos) {
        this.setIndicatieeenmaligelos(indicatieeenmaligelos);
        return this;
    }

    public void setIndicatieeenmaligelos(String indicatieeenmaligelos) {
        this.indicatieeenmaligelos = indicatieeenmaligelos;
    }

    public Boolean getIndicatiemeerjarigeraamovereenkomst() {
        return this.indicatiemeerjarigeraamovereenkomst;
    }

    public Startformulieraanbesteden indicatiemeerjarigeraamovereenkomst(Boolean indicatiemeerjarigeraamovereenkomst) {
        this.setIndicatiemeerjarigeraamovereenkomst(indicatiemeerjarigeraamovereenkomst);
        return this;
    }

    public void setIndicatiemeerjarigeraamovereenkomst(Boolean indicatiemeerjarigeraamovereenkomst) {
        this.indicatiemeerjarigeraamovereenkomst = indicatiemeerjarigeraamovereenkomst;
    }

    public String getIndicatiemeerjarigrepeterend() {
        return this.indicatiemeerjarigrepeterend;
    }

    public Startformulieraanbesteden indicatiemeerjarigrepeterend(String indicatiemeerjarigrepeterend) {
        this.setIndicatiemeerjarigrepeterend(indicatiemeerjarigrepeterend);
        return this;
    }

    public void setIndicatiemeerjarigrepeterend(String indicatiemeerjarigrepeterend) {
        this.indicatiemeerjarigrepeterend = indicatiemeerjarigrepeterend;
    }

    public String getIndicatoroverkoepelendproject() {
        return this.indicatoroverkoepelendproject;
    }

    public Startformulieraanbesteden indicatoroverkoepelendproject(String indicatoroverkoepelendproject) {
        this.setIndicatoroverkoepelendproject(indicatoroverkoepelendproject);
        return this;
    }

    public void setIndicatoroverkoepelendproject(String indicatoroverkoepelendproject) {
        this.indicatoroverkoepelendproject = indicatoroverkoepelendproject;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Startformulieraanbesteden omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpdrachtcategorie() {
        return this.opdrachtcategorie;
    }

    public Startformulieraanbesteden opdrachtcategorie(String opdrachtcategorie) {
        this.setOpdrachtcategorie(opdrachtcategorie);
        return this;
    }

    public void setOpdrachtcategorie(String opdrachtcategorie) {
        this.opdrachtcategorie = opdrachtcategorie;
    }

    public String getOpdrachtsoort() {
        return this.opdrachtsoort;
    }

    public Startformulieraanbesteden opdrachtsoort(String opdrachtsoort) {
        this.setOpdrachtsoort(opdrachtsoort);
        return this;
    }

    public void setOpdrachtsoort(String opdrachtsoort) {
        this.opdrachtsoort = opdrachtsoort;
    }

    public String getToelichtingaanvullendeopdracht() {
        return this.toelichtingaanvullendeopdracht;
    }

    public Startformulieraanbesteden toelichtingaanvullendeopdracht(String toelichtingaanvullendeopdracht) {
        this.setToelichtingaanvullendeopdracht(toelichtingaanvullendeopdracht);
        return this;
    }

    public void setToelichtingaanvullendeopdracht(String toelichtingaanvullendeopdracht) {
        this.toelichtingaanvullendeopdracht = toelichtingaanvullendeopdracht;
    }

    public String getToelichtingeenmaligofrepeterend() {
        return this.toelichtingeenmaligofrepeterend;
    }

    public Startformulieraanbesteden toelichtingeenmaligofrepeterend(String toelichtingeenmaligofrepeterend) {
        this.setToelichtingeenmaligofrepeterend(toelichtingeenmaligofrepeterend);
        return this;
    }

    public void setToelichtingeenmaligofrepeterend(String toelichtingeenmaligofrepeterend) {
        this.toelichtingeenmaligofrepeterend = toelichtingeenmaligofrepeterend;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Startformulieraanbesteden)) {
            return false;
        }
        return getId() != null && getId().equals(((Startformulieraanbesteden) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Startformulieraanbesteden{" +
            "id=" + getId() +
            ", beoogdelooptijd='" + getBeoogdelooptijd() + "'" +
            ", beoogdetotaleopdrachtwaarde=" + getBeoogdetotaleopdrachtwaarde() +
            ", indicatieaanvullendeopdrachtleverancier='" + getIndicatieaanvullendeopdrachtleverancier() + "'" +
            ", indicatiebeoogdeaanbestedingonderhands='" + getIndicatiebeoogdeaanbestedingonderhands() + "'" +
            ", indicatiebeoogdeprockomtovereen='" + getIndicatiebeoogdeprockomtovereen() + "'" +
            ", indicatieeenmaligelos='" + getIndicatieeenmaligelos() + "'" +
            ", indicatiemeerjarigeraamovereenkomst='" + getIndicatiemeerjarigeraamovereenkomst() + "'" +
            ", indicatiemeerjarigrepeterend='" + getIndicatiemeerjarigrepeterend() + "'" +
            ", indicatoroverkoepelendproject='" + getIndicatoroverkoepelendproject() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", opdrachtcategorie='" + getOpdrachtcategorie() + "'" +
            ", opdrachtsoort='" + getOpdrachtsoort() + "'" +
            ", toelichtingaanvullendeopdracht='" + getToelichtingaanvullendeopdracht() + "'" +
            ", toelichtingeenmaligofrepeterend='" + getToelichtingeenmaligofrepeterend() + "'" +
            "}";
    }
}
