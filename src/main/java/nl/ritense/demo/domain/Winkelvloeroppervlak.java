package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Winkelvloeroppervlak.
 */
@Entity
@Table(name = "winkelvloeroppervlak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Winkelvloeroppervlak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalkassa")
    private String aantalkassa;

    @Column(name = "bronwvo")
    private String bronwvo;

    @Column(name = "leegstand")
    private String leegstand;

    @Column(name = "winkelvloeroppervlakte")
    private String winkelvloeroppervlakte;

    @Column(name = "wvoklasse")
    private String wvoklasse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Winkelvloeroppervlak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalkassa() {
        return this.aantalkassa;
    }

    public Winkelvloeroppervlak aantalkassa(String aantalkassa) {
        this.setAantalkassa(aantalkassa);
        return this;
    }

    public void setAantalkassa(String aantalkassa) {
        this.aantalkassa = aantalkassa;
    }

    public String getBronwvo() {
        return this.bronwvo;
    }

    public Winkelvloeroppervlak bronwvo(String bronwvo) {
        this.setBronwvo(bronwvo);
        return this;
    }

    public void setBronwvo(String bronwvo) {
        this.bronwvo = bronwvo;
    }

    public String getLeegstand() {
        return this.leegstand;
    }

    public Winkelvloeroppervlak leegstand(String leegstand) {
        this.setLeegstand(leegstand);
        return this;
    }

    public void setLeegstand(String leegstand) {
        this.leegstand = leegstand;
    }

    public String getWinkelvloeroppervlakte() {
        return this.winkelvloeroppervlakte;
    }

    public Winkelvloeroppervlak winkelvloeroppervlakte(String winkelvloeroppervlakte) {
        this.setWinkelvloeroppervlakte(winkelvloeroppervlakte);
        return this;
    }

    public void setWinkelvloeroppervlakte(String winkelvloeroppervlakte) {
        this.winkelvloeroppervlakte = winkelvloeroppervlakte;
    }

    public String getWvoklasse() {
        return this.wvoklasse;
    }

    public Winkelvloeroppervlak wvoklasse(String wvoklasse) {
        this.setWvoklasse(wvoklasse);
        return this;
    }

    public void setWvoklasse(String wvoklasse) {
        this.wvoklasse = wvoklasse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Winkelvloeroppervlak)) {
            return false;
        }
        return getId() != null && getId().equals(((Winkelvloeroppervlak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Winkelvloeroppervlak{" +
            "id=" + getId() +
            ", aantalkassa='" + getAantalkassa() + "'" +
            ", bronwvo='" + getBronwvo() + "'" +
            ", leegstand='" + getLeegstand() + "'" +
            ", winkelvloeroppervlakte='" + getWinkelvloeroppervlakte() + "'" +
            ", wvoklasse='" + getWvoklasse() + "'" +
            "}";
    }
}
