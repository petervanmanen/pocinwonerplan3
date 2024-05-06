package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Beschiktevoorziening.
 */
@Entity
@Table(name = "beschiktevoorziening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beschiktevoorziening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindeoorspronkelijk")
    private LocalDate datumeindeoorspronkelijk;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "eenheid")
    private String eenheid;

    @Column(name = "frequentie")
    private String frequentie;

    @Column(name = "leveringsvorm")
    private String leveringsvorm;

    @Column(name = "omvang")
    private String omvang;

    @Size(max = 100)
    @Column(name = "redeneinde", length = 100)
    private String redeneinde;

    @Column(name = "status")
    private String status;

    @Column(name = "wet")
    private String wet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beschiktevoorziening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Beschiktevoorziening code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Beschiktevoorziening datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindeoorspronkelijk() {
        return this.datumeindeoorspronkelijk;
    }

    public Beschiktevoorziening datumeindeoorspronkelijk(LocalDate datumeindeoorspronkelijk) {
        this.setDatumeindeoorspronkelijk(datumeindeoorspronkelijk);
        return this;
    }

    public void setDatumeindeoorspronkelijk(LocalDate datumeindeoorspronkelijk) {
        this.datumeindeoorspronkelijk = datumeindeoorspronkelijk;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Beschiktevoorziening datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Beschiktevoorziening eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Beschiktevoorziening frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public String getLeveringsvorm() {
        return this.leveringsvorm;
    }

    public Beschiktevoorziening leveringsvorm(String leveringsvorm) {
        this.setLeveringsvorm(leveringsvorm);
        return this;
    }

    public void setLeveringsvorm(String leveringsvorm) {
        this.leveringsvorm = leveringsvorm;
    }

    public String getOmvang() {
        return this.omvang;
    }

    public Beschiktevoorziening omvang(String omvang) {
        this.setOmvang(omvang);
        return this;
    }

    public void setOmvang(String omvang) {
        this.omvang = omvang;
    }

    public String getRedeneinde() {
        return this.redeneinde;
    }

    public Beschiktevoorziening redeneinde(String redeneinde) {
        this.setRedeneinde(redeneinde);
        return this;
    }

    public void setRedeneinde(String redeneinde) {
        this.redeneinde = redeneinde;
    }

    public String getStatus() {
        return this.status;
    }

    public Beschiktevoorziening status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWet() {
        return this.wet;
    }

    public Beschiktevoorziening wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beschiktevoorziening)) {
            return false;
        }
        return getId() != null && getId().equals(((Beschiktevoorziening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beschiktevoorziening{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindeoorspronkelijk='" + getDatumeindeoorspronkelijk() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", leveringsvorm='" + getLeveringsvorm() + "'" +
            ", omvang='" + getOmvang() + "'" +
            ", redeneinde='" + getRedeneinde() + "'" +
            ", status='" + getStatus() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
