import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './klantcontact.reducer';

export const KlantcontactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const klantcontactEntity = useAppSelector(state => state.klantcontact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="klantcontactDetailsHeading">Klantcontact</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{klantcontactEntity.id}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{klantcontactEntity.eindtijd}</dd>
          <dt>
            <span id="kanaal">Kanaal</span>
          </dt>
          <dd>{klantcontactEntity.kanaal}</dd>
          <dt>
            <span id="notitie">Notitie</span>
          </dt>
          <dd>{klantcontactEntity.notitie}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{klantcontactEntity.starttijd}</dd>
          <dt>
            <span id="tijdsduur">Tijdsduur</span>
          </dt>
          <dd>{klantcontactEntity.tijdsduur}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{klantcontactEntity.toelichting}</dd>
          <dt>
            <span id="wachttijdtotaal">Wachttijdtotaal</span>
          </dt>
          <dd>{klantcontactEntity.wachttijdtotaal}</dd>
          <dt>Heeftklantcontacten Betrokkene</dt>
          <dd>{klantcontactEntity.heeftklantcontactenBetrokkene ? klantcontactEntity.heeftklantcontactenBetrokkene.id : ''}</dd>
          <dt>Heeftbetrekkingop Zaak</dt>
          <dd>{klantcontactEntity.heeftbetrekkingopZaak ? klantcontactEntity.heeftbetrekkingopZaak.id : ''}</dd>
          <dt>Isgevoerddoor Medewerker</dt>
          <dd>{klantcontactEntity.isgevoerddoorMedewerker ? klantcontactEntity.isgevoerddoorMedewerker.id : ''}</dd>
          <dt>Heeft Telefoononderwerp</dt>
          <dd>{klantcontactEntity.heeftTelefoononderwerp ? klantcontactEntity.heeftTelefoononderwerp.id : ''}</dd>
          <dt>Mondtuitin Telefoontje</dt>
          <dd>{klantcontactEntity.mondtuitinTelefoontje ? klantcontactEntity.mondtuitinTelefoontje.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/klantcontact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/klantcontact/${klantcontactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KlantcontactDetail;
