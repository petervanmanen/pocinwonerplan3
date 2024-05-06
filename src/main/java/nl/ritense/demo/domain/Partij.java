package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Partij.
 */
@Entity
@Table(name = "partij")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partij implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "datumaanvanggeldigheidpartij")
    private LocalDate datumaanvanggeldigheidpartij;

    @Column(name = "datumeindegeldigheidpartij")
    private LocalDate datumeindegeldigheidpartij;

    @Column(name = "naam")
    private String naam;

    @Column(name = "soort")
    private String soort;

    @Column(name = "verstrekkingsbeperkingmogelijk")
    private String verstrekkingsbeperkingmogelijk;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partij id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Partij code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumaanvanggeldigheidpartij() {
        return this.datumaanvanggeldigheidpartij;
    }

    public Partij datumaanvanggeldigheidpartij(LocalDate datumaanvanggeldigheidpartij) {
        this.setDatumaanvanggeldigheidpartij(datumaanvanggeldigheidpartij);
        return this;
    }

    public void setDatumaanvanggeldigheidpartij(LocalDate datumaanvanggeldigheidpartij) {
        this.datumaanvanggeldigheidpartij = datumaanvanggeldigheidpartij;
    }

    public LocalDate getDatumeindegeldigheidpartij() {
        return this.datumeindegeldigheidpartij;
    }

    public Partij datumeindegeldigheidpartij(LocalDate datumeindegeldigheidpartij) {
        this.setDatumeindegeldigheidpartij(datumeindegeldigheidpartij);
        return this;
    }

    public void setDatumeindegeldigheidpartij(LocalDate datumeindegeldigheidpartij) {
        this.datumeindegeldigheidpartij = datumeindegeldigheidpartij;
    }

    public String getNaam() {
        return this.naam;
    }

    public Partij naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getSoort() {
        return this.soort;
    }

    public Partij soort(String soort) {
        this.setSoort(soort);
        return this;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public String getVerstrekkingsbeperkingmogelijk() {
        return this.verstrekkingsbeperkingmogelijk;
    }

    public Partij verstrekkingsbeperkingmogelijk(String verstrekkingsbeperkingmogelijk) {
        this.setVerstrekkingsbeperkingmogelijk(verstrekkingsbeperkingmogelijk);
        return this;
    }

    public void setVerstrekkingsbeperkingmogelijk(String verstrekkingsbeperkingmogelijk) {
        this.verstrekkingsbeperkingmogelijk = verstrekkingsbeperkingmogelijk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partij)) {
            return false;
        }
        return getId() != null && getId().equals(((Partij) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partij{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datumaanvanggeldigheidpartij='" + getDatumaanvanggeldigheidpartij() + "'" +
            ", datumeindegeldigheidpartij='" + getDatumeindegeldigheidpartij() + "'" +
            ", naam='" + getNaam() + "'" +
            ", soort='" + getSoort() + "'" +
            ", verstrekkingsbeperkingmogelijk='" + getVerstrekkingsbeperkingmogelijk() + "'" +
            "}";
    }
}
