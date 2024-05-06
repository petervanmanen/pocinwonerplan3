package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Batch.
 */
@Entity
@Table(name = "batch")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "tijd")
    private String tijd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBatch")
    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftBatch" }, allowSetters = true)
    private Set<Batchregel> heeftBatchregels = new HashSet<>();

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
    private Applicatie heeftherkomstApplicatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Batch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Batch datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Batch nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getTijd() {
        return this.tijd;
    }

    public Batch tijd(String tijd) {
        this.setTijd(tijd);
        return this;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public Set<Batchregel> getHeeftBatchregels() {
        return this.heeftBatchregels;
    }

    public void setHeeftBatchregels(Set<Batchregel> batchregels) {
        if (this.heeftBatchregels != null) {
            this.heeftBatchregels.forEach(i -> i.setHeeftBatch(null));
        }
        if (batchregels != null) {
            batchregels.forEach(i -> i.setHeeftBatch(this));
        }
        this.heeftBatchregels = batchregels;
    }

    public Batch heeftBatchregels(Set<Batchregel> batchregels) {
        this.setHeeftBatchregels(batchregels);
        return this;
    }

    public Batch addHeeftBatchregel(Batchregel batchregel) {
        this.heeftBatchregels.add(batchregel);
        batchregel.setHeeftBatch(this);
        return this;
    }

    public Batch removeHeeftBatchregel(Batchregel batchregel) {
        this.heeftBatchregels.remove(batchregel);
        batchregel.setHeeftBatch(null);
        return this;
    }

    public Applicatie getHeeftherkomstApplicatie() {
        return this.heeftherkomstApplicatie;
    }

    public void setHeeftherkomstApplicatie(Applicatie applicatie) {
        this.heeftherkomstApplicatie = applicatie;
    }

    public Batch heeftherkomstApplicatie(Applicatie applicatie) {
        this.setHeeftherkomstApplicatie(applicatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batch)) {
            return false;
        }
        return getId() != null && getId().equals(((Batch) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batch{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", tijd='" + getTijd() + "'" +
            "}";
    }
}
