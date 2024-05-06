import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './balieafspraak.reducer';

export const BalieafspraakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const balieafspraakEntity = useAppSelector(state => state.balieafspraak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="balieafspraakDetailsHeading">Balieafspraak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{balieafspraakEntity.id}</dd>
          <dt>
            <span id="eindtijdgepland">Eindtijdgepland</span>
          </dt>
          <dd>{balieafspraakEntity.eindtijdgepland}</dd>
          <dt>
            <span id="notitie">Notitie</span>
          </dt>
          <dd>{balieafspraakEntity.notitie}</dd>
          <dt>
            <span id="starttijdgepland">Starttijdgepland</span>
          </dt>
          <dd>{balieafspraakEntity.starttijdgepland}</dd>
          <dt>
            <span id="tijdaangemaakt">Tijdaangemaakt</span>
          </dt>
          <dd>{balieafspraakEntity.tijdaangemaakt}</dd>
          <dt>
            <span id="tijdsduurgepland">Tijdsduurgepland</span>
          </dt>
          <dd>{balieafspraakEntity.tijdsduurgepland}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{balieafspraakEntity.toelichting}</dd>
          <dt>
            <span id="wachttijdnastartafspraak">Wachttijdnastartafspraak</span>
          </dt>
          <dd>{balieafspraakEntity.wachttijdnastartafspraak}</dd>
          <dt>
            <span id="wachttijdtotaal">Wachttijdtotaal</span>
          </dt>
          <dd>{balieafspraakEntity.wachttijdtotaal}</dd>
          <dt>
            <span id="wachttijdvoorstartafspraak">Wachttijdvoorstartafspraak</span>
          </dt>
          <dd>{balieafspraakEntity.wachttijdvoorstartafspraak}</dd>
          <dt>
            <span id="werkelijketijdsduur">Werkelijketijdsduur</span>
          </dt>
          <dd>{balieafspraakEntity.werkelijketijdsduur}</dd>
          <dt>Mondtuitin Klantcontact</dt>
          <dd>{balieafspraakEntity.mondtuitinKlantcontact ? balieafspraakEntity.mondtuitinKlantcontact.id : ''}</dd>
          <dt>Heeft Afspraakstatus</dt>
          <dd>{balieafspraakEntity.heeftAfspraakstatus ? balieafspraakEntity.heeftAfspraakstatus.id : ''}</dd>
          <dt>Met Medewerker</dt>
          <dd>{balieafspraakEntity.metMedewerker ? balieafspraakEntity.metMedewerker.id : ''}</dd>
          <dt>Heeftbetrekkingop Zaak</dt>
          <dd>{balieafspraakEntity.heeftbetrekkingopZaak ? balieafspraakEntity.heeftbetrekkingopZaak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/balieafspraak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/balieafspraak/${balieafspraakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BalieafspraakDetail;
