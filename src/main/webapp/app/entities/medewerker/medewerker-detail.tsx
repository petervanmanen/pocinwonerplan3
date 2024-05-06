import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medewerker.reducer';

export const MedewerkerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medewerkerEntity = useAppSelector(state => state.medewerker.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medewerkerDetailsHeading">Medewerker</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{medewerkerEntity.id}</dd>
          <dt>
            <span id="achternaam">Achternaam</span>
          </dt>
          <dd>{medewerkerEntity.achternaam}</dd>
          <dt>
            <span id="datumindienst">Datumindienst</span>
          </dt>
          <dd>
            {medewerkerEntity.datumindienst ? (
              <TextFormat value={medewerkerEntity.datumindienst} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumuitdienst">Datumuitdienst</span>
          </dt>
          <dd>{medewerkerEntity.datumuitdienst}</dd>
          <dt>
            <span id="emailadres">Emailadres</span>
          </dt>
          <dd>{medewerkerEntity.emailadres}</dd>
          <dt>
            <span id="extern">Extern</span>
          </dt>
          <dd>{medewerkerEntity.extern}</dd>
          <dt>
            <span id="functie">Functie</span>
          </dt>
          <dd>{medewerkerEntity.functie}</dd>
          <dt>
            <span id="geslachtsaanduiding">Geslachtsaanduiding</span>
          </dt>
          <dd>{medewerkerEntity.geslachtsaanduiding}</dd>
          <dt>
            <span id="medewerkeridentificatie">Medewerkeridentificatie</span>
          </dt>
          <dd>{medewerkerEntity.medewerkeridentificatie}</dd>
          <dt>
            <span id="medewerkertoelichting">Medewerkertoelichting</span>
          </dt>
          <dd>{medewerkerEntity.medewerkertoelichting}</dd>
          <dt>
            <span id="roepnaam">Roepnaam</span>
          </dt>
          <dd>{medewerkerEntity.roepnaam}</dd>
          <dt>
            <span id="telefoonnummer">Telefoonnummer</span>
          </dt>
          <dd>{medewerkerEntity.telefoonnummer}</dd>
          <dt>
            <span id="voorletters">Voorletters</span>
          </dt>
          <dd>{medewerkerEntity.voorletters}</dd>
          <dt>
            <span id="voorvoegselachternaam">Voorvoegselachternaam</span>
          </dt>
          <dd>{medewerkerEntity.voorvoegselachternaam}</dd>
          <dt>Geleverdvia Leverancier</dt>
          <dd>{medewerkerEntity.geleverdviaLeverancier ? medewerkerEntity.geleverdviaLeverancier.id : ''}</dd>
          <dt>Rollen Applicatie</dt>
          <dd>
            {medewerkerEntity.rollenApplicaties
              ? medewerkerEntity.rollenApplicaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {medewerkerEntity.rollenApplicaties && i === medewerkerEntity.rollenApplicaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Afhandelendmedewerker Zaak</dt>
          <dd>
            {medewerkerEntity.afhandelendmedewerkerZaaks
              ? medewerkerEntity.afhandelendmedewerkerZaaks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {medewerkerEntity.afhandelendmedewerkerZaaks && i === medewerkerEntity.afhandelendmedewerkerZaaks.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/medewerker" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medewerker/${medewerkerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedewerkerDetail;
