package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verplichtingwmojeugd.
 */
@Entity
@Table(name = "verplichtingwmojeugd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verplichtingwmojeugd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "budgetsoort")
    private String budgetsoort;

    @Column(name = "budgetsoortgroep")
    private String budgetsoortgroep;

    @Column(name = "einddatumgepland")
    private String einddatumgepland;

    @Column(name = "feitelijkeeinddatum")
    private String feitelijkeeinddatum;

    @Column(name = "jaar")
    private String jaar;

    @Column(name = "periodiciteit")
    private String periodiciteit;

    @Column(name = "verplichtingsoort")
    private String verplichtingsoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verplichtingwmojeugd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBudgetsoort() {
        return this.budgetsoort;
    }

    public Verplichtingwmojeugd budgetsoort(String budgetsoort) {
        this.setBudgetsoort(budgetsoort);
        return this;
    }

    public void setBudgetsoort(String budgetsoort) {
        this.budgetsoort = budgetsoort;
    }

    public String getBudgetsoortgroep() {
        return this.budgetsoortgroep;
    }

    public Verplichtingwmojeugd budgetsoortgroep(String budgetsoortgroep) {
        this.setBudgetsoortgroep(budgetsoortgroep);
        return this;
    }

    public void setBudgetsoortgroep(String budgetsoortgroep) {
        this.budgetsoortgroep = budgetsoortgroep;
    }

    public String getEinddatumgepland() {
        return this.einddatumgepland;
    }

    public Verplichtingwmojeugd einddatumgepland(String einddatumgepland) {
        this.setEinddatumgepland(einddatumgepland);
        return this;
    }

    public void setEinddatumgepland(String einddatumgepland) {
        this.einddatumgepland = einddatumgepland;
    }

    public String getFeitelijkeeinddatum() {
        return this.feitelijkeeinddatum;
    }

    public Verplichtingwmojeugd feitelijkeeinddatum(String feitelijkeeinddatum) {
        this.setFeitelijkeeinddatum(feitelijkeeinddatum);
        return this;
    }

    public void setFeitelijkeeinddatum(String feitelijkeeinddatum) {
        this.feitelijkeeinddatum = feitelijkeeinddatum;
    }

    public String getJaar() {
        return this.jaar;
    }

    public Verplichtingwmojeugd jaar(String jaar) {
        this.setJaar(jaar);
        return this;
    }

    public void setJaar(String jaar) {
        this.jaar = jaar;
    }

    public String getPeriodiciteit() {
        return this.periodiciteit;
    }

    public Verplichtingwmojeugd periodiciteit(String periodiciteit) {
        this.setPeriodiciteit(periodiciteit);
        return this;
    }

    public void setPeriodiciteit(String periodiciteit) {
        this.periodiciteit = periodiciteit;
    }

    public String getVerplichtingsoort() {
        return this.verplichtingsoort;
    }

    public Verplichtingwmojeugd verplichtingsoort(String verplichtingsoort) {
        this.setVerplichtingsoort(verplichtingsoort);
        return this;
    }

    public void setVerplichtingsoort(String verplichtingsoort) {
        this.verplichtingsoort = verplichtingsoort;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verplichtingwmojeugd)) {
            return false;
        }
        return getId() != null && getId().equals(((Verplichtingwmojeugd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verplichtingwmojeugd{" +
            "id=" + getId() +
            ", budgetsoort='" + getBudgetsoort() + "'" +
            ", budgetsoortgroep='" + getBudgetsoortgroep() + "'" +
            ", einddatumgepland='" + getEinddatumgepland() + "'" +
            ", feitelijkeeinddatum='" + getFeitelijkeeinddatum() + "'" +
            ", jaar='" + getJaar() + "'" +
            ", periodiciteit='" + getPeriodiciteit() + "'" +
            ", verplichtingsoort='" + getVerplichtingsoort() + "'" +
            "}";
    }
}
