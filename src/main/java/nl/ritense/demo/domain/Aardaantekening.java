package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aardaantekening.
 */
@Entity
@Table(name = "aardaantekening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aardaantekening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codeaardaantekening")
    private String codeaardaantekening;

    @Column(name = "datumbegingeldigheidaardaantekening")
    private LocalDate datumbegingeldigheidaardaantekening;

    @Column(name = "datumeindegeldigheidaardaantekening")
    private LocalDate datumeindegeldigheidaardaantekening;

    @Column(name = "naamaardaantekening")
    private String naamaardaantekening;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aardaantekening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeaardaantekening() {
        return this.codeaardaantekening;
    }

    public Aardaantekening codeaardaantekening(String codeaardaantekening) {
        this.setCodeaardaantekening(codeaardaantekening);
        return this;
    }

    public void setCodeaardaantekening(String codeaardaantekening) {
        this.codeaardaantekening = codeaardaantekening;
    }

    public LocalDate getDatumbegingeldigheidaardaantekening() {
        return this.datumbegingeldigheidaardaantekening;
    }

    public Aardaantekening datumbegingeldigheidaardaantekening(LocalDate datumbegingeldigheidaardaantekening) {
        this.setDatumbegingeldigheidaardaantekening(datumbegingeldigheidaardaantekening);
        return this;
    }

    public void setDatumbegingeldigheidaardaantekening(LocalDate datumbegingeldigheidaardaantekening) {
        this.datumbegingeldigheidaardaantekening = datumbegingeldigheidaardaantekening;
    }

    public LocalDate getDatumeindegeldigheidaardaantekening() {
        return this.datumeindegeldigheidaardaantekening;
    }

    public Aardaantekening datumeindegeldigheidaardaantekening(LocalDate datumeindegeldigheidaardaantekening) {
        this.setDatumeindegeldigheidaardaantekening(datumeindegeldigheidaardaantekening);
        return this;
    }

    public void setDatumeindegeldigheidaardaantekening(LocalDate datumeindegeldigheidaardaantekening) {
        this.datumeindegeldigheidaardaantekening = datumeindegeldigheidaardaantekening;
    }

    public String getNaamaardaantekening() {
        return this.naamaardaantekening;
    }

    public Aardaantekening naamaardaantekening(String naamaardaantekening) {
        this.setNaamaardaantekening(naamaardaantekening);
        return this;
    }

    public void setNaamaardaantekening(String naamaardaantekening) {
        this.naamaardaantekening = naamaardaantekening;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aardaantekening)) {
            return false;
        }
        return getId() != null && getId().equals(((Aardaantekening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aardaantekening{" +
            "id=" + getId() +
            ", codeaardaantekening='" + getCodeaardaantekening() + "'" +
            ", datumbegingeldigheidaardaantekening='" + getDatumbegingeldigheidaardaantekening() + "'" +
            ", datumeindegeldigheidaardaantekening='" + getDatumeindegeldigheidaardaantekening() + "'" +
            ", naamaardaantekening='" + getNaamaardaantekening() + "'" +
            "}";
    }
}
