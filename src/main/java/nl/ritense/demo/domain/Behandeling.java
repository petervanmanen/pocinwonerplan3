package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Behandeling.
 */
@Entity
@Table(name = "behandeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Behandeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "toelichting")
    private String toelichting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvansoortBehandelings" }, allowSetters = true)
    private Behandelsoort isvansoortBehandelsoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Behandeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Behandeling datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Behandeling datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Behandeling toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public Behandelsoort getIsvansoortBehandelsoort() {
        return this.isvansoortBehandelsoort;
    }

    public void setIsvansoortBehandelsoort(Behandelsoort behandelsoort) {
        this.isvansoortBehandelsoort = behandelsoort;
    }

    public Behandeling isvansoortBehandelsoort(Behandelsoort behandelsoort) {
        this.setIsvansoortBehandelsoort(behandelsoort);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Behandeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Behandeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Behandeling{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
