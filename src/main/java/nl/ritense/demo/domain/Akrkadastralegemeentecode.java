package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Akrkadastralegemeentecode.
 */
@Entity
@Table(name = "akrkadastralegemeentecode")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Akrkadastralegemeentecode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "akrcode")
    private String akrcode;

    @Column(name = "codeakrkadadastralegemeentecode")
    private String codeakrkadadastralegemeentecode;

    @Column(name = "datumbegingeldigheidakrcode")
    private LocalDate datumbegingeldigheidakrcode;

    @Column(name = "datumeindegeldigheidakrcode")
    private LocalDate datumeindegeldigheidakrcode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Akrkadastralegemeentecode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAkrcode() {
        return this.akrcode;
    }

    public Akrkadastralegemeentecode akrcode(String akrcode) {
        this.setAkrcode(akrcode);
        return this;
    }

    public void setAkrcode(String akrcode) {
        this.akrcode = akrcode;
    }

    public String getCodeakrkadadastralegemeentecode() {
        return this.codeakrkadadastralegemeentecode;
    }

    public Akrkadastralegemeentecode codeakrkadadastralegemeentecode(String codeakrkadadastralegemeentecode) {
        this.setCodeakrkadadastralegemeentecode(codeakrkadadastralegemeentecode);
        return this;
    }

    public void setCodeakrkadadastralegemeentecode(String codeakrkadadastralegemeentecode) {
        this.codeakrkadadastralegemeentecode = codeakrkadadastralegemeentecode;
    }

    public LocalDate getDatumbegingeldigheidakrcode() {
        return this.datumbegingeldigheidakrcode;
    }

    public Akrkadastralegemeentecode datumbegingeldigheidakrcode(LocalDate datumbegingeldigheidakrcode) {
        this.setDatumbegingeldigheidakrcode(datumbegingeldigheidakrcode);
        return this;
    }

    public void setDatumbegingeldigheidakrcode(LocalDate datumbegingeldigheidakrcode) {
        this.datumbegingeldigheidakrcode = datumbegingeldigheidakrcode;
    }

    public LocalDate getDatumeindegeldigheidakrcode() {
        return this.datumeindegeldigheidakrcode;
    }

    public Akrkadastralegemeentecode datumeindegeldigheidakrcode(LocalDate datumeindegeldigheidakrcode) {
        this.setDatumeindegeldigheidakrcode(datumeindegeldigheidakrcode);
        return this;
    }

    public void setDatumeindegeldigheidakrcode(LocalDate datumeindegeldigheidakrcode) {
        this.datumeindegeldigheidakrcode = datumeindegeldigheidakrcode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Akrkadastralegemeentecode)) {
            return false;
        }
        return getId() != null && getId().equals(((Akrkadastralegemeentecode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Akrkadastralegemeentecode{" +
            "id=" + getId() +
            ", akrcode='" + getAkrcode() + "'" +
            ", codeakrkadadastralegemeentecode='" + getCodeakrkadadastralegemeentecode() + "'" +
            ", datumbegingeldigheidakrcode='" + getDatumbegingeldigheidakrcode() + "'" +
            ", datumeindegeldigheidakrcode='" + getDatumeindegeldigheidakrcode() + "'" +
            "}";
    }
}
