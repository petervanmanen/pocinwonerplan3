package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Informatiedakloosheid.
 */
@Entity
@Table(name = "informatiedakloosheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Informatiedakloosheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "gemeenteoorsprong")
    private String gemeenteoorsprong;

    @Column(name = "toestemminggemeentelijkbriefadres")
    private Boolean toestemminggemeentelijkbriefadres;

    @Column(name = "toestemmingnachtopvang")
    private Boolean toestemmingnachtopvang;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Informatiedakloosheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Informatiedakloosheid datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Informatiedakloosheid datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getGemeenteoorsprong() {
        return this.gemeenteoorsprong;
    }

    public Informatiedakloosheid gemeenteoorsprong(String gemeenteoorsprong) {
        this.setGemeenteoorsprong(gemeenteoorsprong);
        return this;
    }

    public void setGemeenteoorsprong(String gemeenteoorsprong) {
        this.gemeenteoorsprong = gemeenteoorsprong;
    }

    public Boolean getToestemminggemeentelijkbriefadres() {
        return this.toestemminggemeentelijkbriefadres;
    }

    public Informatiedakloosheid toestemminggemeentelijkbriefadres(Boolean toestemminggemeentelijkbriefadres) {
        this.setToestemminggemeentelijkbriefadres(toestemminggemeentelijkbriefadres);
        return this;
    }

    public void setToestemminggemeentelijkbriefadres(Boolean toestemminggemeentelijkbriefadres) {
        this.toestemminggemeentelijkbriefadres = toestemminggemeentelijkbriefadres;
    }

    public Boolean getToestemmingnachtopvang() {
        return this.toestemmingnachtopvang;
    }

    public Informatiedakloosheid toestemmingnachtopvang(Boolean toestemmingnachtopvang) {
        this.setToestemmingnachtopvang(toestemmingnachtopvang);
        return this;
    }

    public void setToestemmingnachtopvang(Boolean toestemmingnachtopvang) {
        this.toestemmingnachtopvang = toestemmingnachtopvang;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Informatiedakloosheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Informatiedakloosheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Informatiedakloosheid{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", gemeenteoorsprong='" + getGemeenteoorsprong() + "'" +
            ", toestemminggemeentelijkbriefadres='" + getToestemminggemeentelijkbriefadres() + "'" +
            ", toestemmingnachtopvang='" + getToestemmingnachtopvang() + "'" +
            "}";
    }
}
