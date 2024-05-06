package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Epackage.
 */
@Entity
@Table(name = "epackage")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Epackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "proces")
    private String proces;

    @Column(name = "project")
    private String project;

    @Column(name = "status")
    private String status;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Epackage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Epackage naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getProces() {
        return this.proces;
    }

    public Epackage proces(String proces) {
        this.setProces(proces);
        return this;
    }

    public void setProces(String proces) {
        this.proces = proces;
    }

    public String getProject() {
        return this.project;
    }

    public Epackage project(String project) {
        this.setProject(project);
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStatus() {
        return this.status;
    }

    public Epackage status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Epackage toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Epackage)) {
            return false;
        }
        return getId() != null && getId().equals(((Epackage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Epackage{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", proces='" + getProces() + "'" +
            ", project='" + getProject() + "'" +
            ", status='" + getStatus() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
