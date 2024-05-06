package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Vulgraadmeting.
 */
@Entity
@Table(name = "vulgraadmeting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vulgraadmeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tijdstip")
    private String tijdstip;

    @Column(name = "vulgraad")
    private String vulgraad;

    @Column(name = "vullinggewicht")
    private String vullinggewicht;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftVulgraadmetings", "geschiktvoorFractie", "soortContainertype", "heeftLocatie", "gelostOphaalmoments" },
        allowSetters = true
    )
    private Container heeftContainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vulgraadmeting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTijdstip() {
        return this.tijdstip;
    }

    public Vulgraadmeting tijdstip(String tijdstip) {
        this.setTijdstip(tijdstip);
        return this;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getVulgraad() {
        return this.vulgraad;
    }

    public Vulgraadmeting vulgraad(String vulgraad) {
        this.setVulgraad(vulgraad);
        return this;
    }

    public void setVulgraad(String vulgraad) {
        this.vulgraad = vulgraad;
    }

    public String getVullinggewicht() {
        return this.vullinggewicht;
    }

    public Vulgraadmeting vullinggewicht(String vullinggewicht) {
        this.setVullinggewicht(vullinggewicht);
        return this;
    }

    public void setVullinggewicht(String vullinggewicht) {
        this.vullinggewicht = vullinggewicht;
    }

    public Container getHeeftContainer() {
        return this.heeftContainer;
    }

    public void setHeeftContainer(Container container) {
        this.heeftContainer = container;
    }

    public Vulgraadmeting heeftContainer(Container container) {
        this.setHeeftContainer(container);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vulgraadmeting)) {
            return false;
        }
        return getId() != null && getId().equals(((Vulgraadmeting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vulgraadmeting{" +
            "id=" + getId() +
            ", tijdstip='" + getTijdstip() + "'" +
            ", vulgraad='" + getVulgraad() + "'" +
            ", vullinggewicht='" + getVullinggewicht() + "'" +
            "}";
    }
}
