package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Telefoononderwerp.
 */
@Entity
@Table(name = "telefoononderwerp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Telefoononderwerp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "onderwerp")
    private String onderwerp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftTelefoononderwerp")
    @JsonIgnoreProperties(
        value = {
            "heeftklantcontactenBetrokkene",
            "heeftbetrekkingopZaak",
            "isgevoerddoorMedewerker",
            "mondtuitinBalieafspraak",
            "heeftTelefoononderwerp",
            "mondtuitinTelefoontje",
        },
        allowSetters = true
    )
    private Set<Klantcontact> heeftKlantcontacts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftTelefoononderwerp")
    @JsonIgnoreProperties(value = { "mondtuitinKlantcontacts", "heeftTelefoonstatus", "heeftTelefoononderwerp" }, allowSetters = true)
    private Set<Telefoontje> heeftTelefoontjes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Telefoononderwerp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnderwerp() {
        return this.onderwerp;
    }

    public Telefoononderwerp onderwerp(String onderwerp) {
        this.setOnderwerp(onderwerp);
        return this;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public Set<Klantcontact> getHeeftKlantcontacts() {
        return this.heeftKlantcontacts;
    }

    public void setHeeftKlantcontacts(Set<Klantcontact> klantcontacts) {
        if (this.heeftKlantcontacts != null) {
            this.heeftKlantcontacts.forEach(i -> i.setHeeftTelefoononderwerp(null));
        }
        if (klantcontacts != null) {
            klantcontacts.forEach(i -> i.setHeeftTelefoononderwerp(this));
        }
        this.heeftKlantcontacts = klantcontacts;
    }

    public Telefoononderwerp heeftKlantcontacts(Set<Klantcontact> klantcontacts) {
        this.setHeeftKlantcontacts(klantcontacts);
        return this;
    }

    public Telefoononderwerp addHeeftKlantcontact(Klantcontact klantcontact) {
        this.heeftKlantcontacts.add(klantcontact);
        klantcontact.setHeeftTelefoononderwerp(this);
        return this;
    }

    public Telefoononderwerp removeHeeftKlantcontact(Klantcontact klantcontact) {
        this.heeftKlantcontacts.remove(klantcontact);
        klantcontact.setHeeftTelefoononderwerp(null);
        return this;
    }

    public Set<Telefoontje> getHeeftTelefoontjes() {
        return this.heeftTelefoontjes;
    }

    public void setHeeftTelefoontjes(Set<Telefoontje> telefoontjes) {
        if (this.heeftTelefoontjes != null) {
            this.heeftTelefoontjes.forEach(i -> i.setHeeftTelefoononderwerp(null));
        }
        if (telefoontjes != null) {
            telefoontjes.forEach(i -> i.setHeeftTelefoononderwerp(this));
        }
        this.heeftTelefoontjes = telefoontjes;
    }

    public Telefoononderwerp heeftTelefoontjes(Set<Telefoontje> telefoontjes) {
        this.setHeeftTelefoontjes(telefoontjes);
        return this;
    }

    public Telefoononderwerp addHeeftTelefoontje(Telefoontje telefoontje) {
        this.heeftTelefoontjes.add(telefoontje);
        telefoontje.setHeeftTelefoononderwerp(this);
        return this;
    }

    public Telefoononderwerp removeHeeftTelefoontje(Telefoontje telefoontje) {
        this.heeftTelefoontjes.remove(telefoontje);
        telefoontje.setHeeftTelefoononderwerp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telefoononderwerp)) {
            return false;
        }
        return getId() != null && getId().equals(((Telefoononderwerp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefoononderwerp{" +
            "id=" + getId() +
            ", onderwerp='" + getOnderwerp() + "'" +
            "}";
    }
}
