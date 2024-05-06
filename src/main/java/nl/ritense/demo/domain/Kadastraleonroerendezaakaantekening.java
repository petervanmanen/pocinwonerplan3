package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Kadastraleonroerendezaakaantekening.
 */
@Entity
@Table(name = "kadastraleonroerendezaakaantekening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kadastraleonroerendezaakaantekening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aardaantekeningkadastraalobject")
    private String aardaantekeningkadastraalobject;

    @Column(name = "beschrijvingaantekeningkadastraalobject")
    private String beschrijvingaantekeningkadastraalobject;

    @Column(name = "datumbeginaantekeningkadastraalobject")
    private LocalDate datumbeginaantekeningkadastraalobject;

    @Column(name = "datumeindeaantekeningkadastraalobject")
    private LocalDate datumeindeaantekeningkadastraalobject;

    @Column(name = "kadasteridentificatieaantekening")
    private String kadasteridentificatieaantekening;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kadastraleonroerendezaakaantekening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAardaantekeningkadastraalobject() {
        return this.aardaantekeningkadastraalobject;
    }

    public Kadastraleonroerendezaakaantekening aardaantekeningkadastraalobject(String aardaantekeningkadastraalobject) {
        this.setAardaantekeningkadastraalobject(aardaantekeningkadastraalobject);
        return this;
    }

    public void setAardaantekeningkadastraalobject(String aardaantekeningkadastraalobject) {
        this.aardaantekeningkadastraalobject = aardaantekeningkadastraalobject;
    }

    public String getBeschrijvingaantekeningkadastraalobject() {
        return this.beschrijvingaantekeningkadastraalobject;
    }

    public Kadastraleonroerendezaakaantekening beschrijvingaantekeningkadastraalobject(String beschrijvingaantekeningkadastraalobject) {
        this.setBeschrijvingaantekeningkadastraalobject(beschrijvingaantekeningkadastraalobject);
        return this;
    }

    public void setBeschrijvingaantekeningkadastraalobject(String beschrijvingaantekeningkadastraalobject) {
        this.beschrijvingaantekeningkadastraalobject = beschrijvingaantekeningkadastraalobject;
    }

    public LocalDate getDatumbeginaantekeningkadastraalobject() {
        return this.datumbeginaantekeningkadastraalobject;
    }

    public Kadastraleonroerendezaakaantekening datumbeginaantekeningkadastraalobject(LocalDate datumbeginaantekeningkadastraalobject) {
        this.setDatumbeginaantekeningkadastraalobject(datumbeginaantekeningkadastraalobject);
        return this;
    }

    public void setDatumbeginaantekeningkadastraalobject(LocalDate datumbeginaantekeningkadastraalobject) {
        this.datumbeginaantekeningkadastraalobject = datumbeginaantekeningkadastraalobject;
    }

    public LocalDate getDatumeindeaantekeningkadastraalobject() {
        return this.datumeindeaantekeningkadastraalobject;
    }

    public Kadastraleonroerendezaakaantekening datumeindeaantekeningkadastraalobject(LocalDate datumeindeaantekeningkadastraalobject) {
        this.setDatumeindeaantekeningkadastraalobject(datumeindeaantekeningkadastraalobject);
        return this;
    }

    public void setDatumeindeaantekeningkadastraalobject(LocalDate datumeindeaantekeningkadastraalobject) {
        this.datumeindeaantekeningkadastraalobject = datumeindeaantekeningkadastraalobject;
    }

    public String getKadasteridentificatieaantekening() {
        return this.kadasteridentificatieaantekening;
    }

    public Kadastraleonroerendezaakaantekening kadasteridentificatieaantekening(String kadasteridentificatieaantekening) {
        this.setKadasteridentificatieaantekening(kadasteridentificatieaantekening);
        return this;
    }

    public void setKadasteridentificatieaantekening(String kadasteridentificatieaantekening) {
        this.kadasteridentificatieaantekening = kadasteridentificatieaantekening;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kadastraleonroerendezaakaantekening)) {
            return false;
        }
        return getId() != null && getId().equals(((Kadastraleonroerendezaakaantekening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kadastraleonroerendezaakaantekening{" +
            "id=" + getId() +
            ", aardaantekeningkadastraalobject='" + getAardaantekeningkadastraalobject() + "'" +
            ", beschrijvingaantekeningkadastraalobject='" + getBeschrijvingaantekeningkadastraalobject() + "'" +
            ", datumbeginaantekeningkadastraalobject='" + getDatumbeginaantekeningkadastraalobject() + "'" +
            ", datumeindeaantekeningkadastraalobject='" + getDatumeindeaantekeningkadastraalobject() + "'" +
            ", kadasteridentificatieaantekening='" + getKadasteridentificatieaantekening() + "'" +
            "}";
    }
}
