package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Kpclassaclassc.
 */
@Entity
@Table(name = "kpclassaclassc")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kpclassaclassc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mbronsysteem")
    private String mbronsysteem;

    @Column(name = "mdatumtijdgeladen")
    private String mdatumtijdgeladen;

    @Column(name = "classcid")
    private Integer classcid;

    @Column(name = "classaid")
    private Integer classaid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kpclassaclassc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMbronsysteem() {
        return this.mbronsysteem;
    }

    public Kpclassaclassc mbronsysteem(String mbronsysteem) {
        this.setMbronsysteem(mbronsysteem);
        return this;
    }

    public void setMbronsysteem(String mbronsysteem) {
        this.mbronsysteem = mbronsysteem;
    }

    public String getMdatumtijdgeladen() {
        return this.mdatumtijdgeladen;
    }

    public Kpclassaclassc mdatumtijdgeladen(String mdatumtijdgeladen) {
        this.setMdatumtijdgeladen(mdatumtijdgeladen);
        return this;
    }

    public void setMdatumtijdgeladen(String mdatumtijdgeladen) {
        this.mdatumtijdgeladen = mdatumtijdgeladen;
    }

    public Integer getClasscid() {
        return this.classcid;
    }

    public Kpclassaclassc classcid(Integer classcid) {
        this.setClasscid(classcid);
        return this;
    }

    public void setClasscid(Integer classcid) {
        this.classcid = classcid;
    }

    public Integer getClassaid() {
        return this.classaid;
    }

    public Kpclassaclassc classaid(Integer classaid) {
        this.setClassaid(classaid);
        return this;
    }

    public void setClassaid(Integer classaid) {
        this.classaid = classaid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kpclassaclassc)) {
            return false;
        }
        return getId() != null && getId().equals(((Kpclassaclassc) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kpclassaclassc{" +
            "id=" + getId() +
            ", mbronsysteem='" + getMbronsysteem() + "'" +
            ", mdatumtijdgeladen='" + getMdatumtijdgeladen() + "'" +
            ", classcid=" + getClasscid() +
            ", classaid=" + getClassaid() +
            "}";
    }
}
