package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inzet.
 */
@Entity
@Table(name = "inzet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inzet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegin")
    private LocalDate datumbegin;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Size(max = 20)
    @Column(name = "percentage", length = 20)
    private String percentage;

    @Size(max = 20)
    @Column(name = "uren", length = 20)
    private String uren;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aantalvolgensinzetInzet")
    @JsonIgnoreProperties(
        value = {
            "dienstverbandconformfunctieFunctie",
            "aantalvolgensinzetUren",
            "medewerkerheeftdienstverbandWerknemer",
            "aantalvolgensinzetInzet",
        },
        allowSetters = true
    )
    private Set<Dienstverband> aantalvolgensinzetDienstverbands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inzet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegin() {
        return this.datumbegin;
    }

    public Inzet datumbegin(LocalDate datumbegin) {
        this.setDatumbegin(datumbegin);
        return this;
    }

    public void setDatumbegin(LocalDate datumbegin) {
        this.datumbegin = datumbegin;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Inzet datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getPercentage() {
        return this.percentage;
    }

    public Inzet percentage(String percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getUren() {
        return this.uren;
    }

    public Inzet uren(String uren) {
        this.setUren(uren);
        return this;
    }

    public void setUren(String uren) {
        this.uren = uren;
    }

    public Set<Dienstverband> getAantalvolgensinzetDienstverbands() {
        return this.aantalvolgensinzetDienstverbands;
    }

    public void setAantalvolgensinzetDienstverbands(Set<Dienstverband> dienstverbands) {
        if (this.aantalvolgensinzetDienstverbands != null) {
            this.aantalvolgensinzetDienstverbands.forEach(i -> i.setAantalvolgensinzetInzet(null));
        }
        if (dienstverbands != null) {
            dienstverbands.forEach(i -> i.setAantalvolgensinzetInzet(this));
        }
        this.aantalvolgensinzetDienstverbands = dienstverbands;
    }

    public Inzet aantalvolgensinzetDienstverbands(Set<Dienstverband> dienstverbands) {
        this.setAantalvolgensinzetDienstverbands(dienstverbands);
        return this;
    }

    public Inzet addAantalvolgensinzetDienstverband(Dienstverband dienstverband) {
        this.aantalvolgensinzetDienstverbands.add(dienstverband);
        dienstverband.setAantalvolgensinzetInzet(this);
        return this;
    }

    public Inzet removeAantalvolgensinzetDienstverband(Dienstverband dienstverband) {
        this.aantalvolgensinzetDienstverbands.remove(dienstverband);
        dienstverband.setAantalvolgensinzetInzet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inzet)) {
            return false;
        }
        return getId() != null && getId().equals(((Inzet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inzet{" +
            "id=" + getId() +
            ", datumbegin='" + getDatumbegin() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", percentage='" + getPercentage() + "'" +
            ", uren='" + getUren() + "'" +
            "}";
    }
}
