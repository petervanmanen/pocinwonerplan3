package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Bevinding.
 */
@Entity
@Table(name = "bevinding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bevinding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Column(name = "activiteit")
    private String activiteit;

    @Column(name = "controleelement")
    private String controleelement;

    @Column(name = "controleniveau")
    private String controleniveau;

    @Column(name = "datumaanmaak")
    private LocalDate datumaanmaak;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Size(max = 20)
    @Column(name = "diepte", length = 20)
    private String diepte;

    @Size(max = 20)
    @Column(name = "fase", length = 20)
    private String fase;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Column(name = "resultaat")
    private String resultaat;

    @Column(name = "risico")
    private String risico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bevinding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Bevinding aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public String getActiviteit() {
        return this.activiteit;
    }

    public Bevinding activiteit(String activiteit) {
        this.setActiviteit(activiteit);
        return this;
    }

    public void setActiviteit(String activiteit) {
        this.activiteit = activiteit;
    }

    public String getControleelement() {
        return this.controleelement;
    }

    public Bevinding controleelement(String controleelement) {
        this.setControleelement(controleelement);
        return this;
    }

    public void setControleelement(String controleelement) {
        this.controleelement = controleelement;
    }

    public String getControleniveau() {
        return this.controleniveau;
    }

    public Bevinding controleniveau(String controleniveau) {
        this.setControleniveau(controleniveau);
        return this;
    }

    public void setControleniveau(String controleniveau) {
        this.controleniveau = controleniveau;
    }

    public LocalDate getDatumaanmaak() {
        return this.datumaanmaak;
    }

    public Bevinding datumaanmaak(LocalDate datumaanmaak) {
        this.setDatumaanmaak(datumaanmaak);
        return this;
    }

    public void setDatumaanmaak(LocalDate datumaanmaak) {
        this.datumaanmaak = datumaanmaak;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Bevinding datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public String getDiepte() {
        return this.diepte;
    }

    public Bevinding diepte(String diepte) {
        this.setDiepte(diepte);
        return this;
    }

    public void setDiepte(String diepte) {
        this.diepte = diepte;
    }

    public String getFase() {
        return this.fase;
    }

    public Bevinding fase(String fase) {
        this.setFase(fase);
        return this;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Bevinding gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public String getResultaat() {
        return this.resultaat;
    }

    public Bevinding resultaat(String resultaat) {
        this.setResultaat(resultaat);
        return this;
    }

    public void setResultaat(String resultaat) {
        this.resultaat = resultaat;
    }

    public String getRisico() {
        return this.risico;
    }

    public Bevinding risico(String risico) {
        this.setRisico(risico);
        return this;
    }

    public void setRisico(String risico) {
        this.risico = risico;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bevinding)) {
            return false;
        }
        return getId() != null && getId().equals(((Bevinding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bevinding{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", activiteit='" + getActiviteit() + "'" +
            ", controleelement='" + getControleelement() + "'" +
            ", controleniveau='" + getControleniveau() + "'" +
            ", datumaanmaak='" + getDatumaanmaak() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", diepte='" + getDiepte() + "'" +
            ", fase='" + getFase() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", resultaat='" + getResultaat() + "'" +
            ", risico='" + getRisico() + "'" +
            "}";
    }
}
