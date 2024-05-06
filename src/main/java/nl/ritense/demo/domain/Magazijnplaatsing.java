package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Magazijnplaatsing.
 */
@Entity
@Table(name = "magazijnplaatsing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Magazijnplaatsing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "datumgeplaatst")
    private LocalDate datumgeplaatst;

    @Column(name = "herkomst")
    private String herkomst;

    @Column(name = "key")
    private String key;

    @Column(name = "keydoos")
    private String keydoos;

    @Column(name = "keymagazijnlocatie")
    private String keymagazijnlocatie;

    @Column(name = "projectcd")
    private String projectcd;

    @Column(name = "uitgeleend")
    private Boolean uitgeleend;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Magazijnplaatsing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Magazijnplaatsing beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public LocalDate getDatumgeplaatst() {
        return this.datumgeplaatst;
    }

    public Magazijnplaatsing datumgeplaatst(LocalDate datumgeplaatst) {
        this.setDatumgeplaatst(datumgeplaatst);
        return this;
    }

    public void setDatumgeplaatst(LocalDate datumgeplaatst) {
        this.datumgeplaatst = datumgeplaatst;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Magazijnplaatsing herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getKey() {
        return this.key;
    }

    public Magazijnplaatsing key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeydoos() {
        return this.keydoos;
    }

    public Magazijnplaatsing keydoos(String keydoos) {
        this.setKeydoos(keydoos);
        return this;
    }

    public void setKeydoos(String keydoos) {
        this.keydoos = keydoos;
    }

    public String getKeymagazijnlocatie() {
        return this.keymagazijnlocatie;
    }

    public Magazijnplaatsing keymagazijnlocatie(String keymagazijnlocatie) {
        this.setKeymagazijnlocatie(keymagazijnlocatie);
        return this;
    }

    public void setKeymagazijnlocatie(String keymagazijnlocatie) {
        this.keymagazijnlocatie = keymagazijnlocatie;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Magazijnplaatsing projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public Boolean getUitgeleend() {
        return this.uitgeleend;
    }

    public Magazijnplaatsing uitgeleend(Boolean uitgeleend) {
        this.setUitgeleend(uitgeleend);
        return this;
    }

    public void setUitgeleend(Boolean uitgeleend) {
        this.uitgeleend = uitgeleend;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Magazijnplaatsing)) {
            return false;
        }
        return getId() != null && getId().equals(((Magazijnplaatsing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Magazijnplaatsing{" +
            "id=" + getId() +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", datumgeplaatst='" + getDatumgeplaatst() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", key='" + getKey() + "'" +
            ", keydoos='" + getKeydoos() + "'" +
            ", keymagazijnlocatie='" + getKeymagazijnlocatie() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", uitgeleend='" + getUitgeleend() + "'" +
            "}";
    }
}
