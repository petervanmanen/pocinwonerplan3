import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zaakorigineel.reducer';

export const ZaakorigineelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zaakorigineelEntity = useAppSelector(state => state.zaakorigineel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zaakorigineelDetailsHeading">Zaakorigineel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zaakorigineelEntity.id}</dd>
          <dt>
            <span id="anderzaakobject">Anderzaakobject</span>
          </dt>
          <dd>{zaakorigineelEntity.anderzaakobject}</dd>
          <dt>
            <span id="archiefnominatie">Archiefnominatie</span>
          </dt>
          <dd>{zaakorigineelEntity.archiefnominatie}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{zaakorigineelEntity.datumeinde}</dd>
          <dt>
            <span id="datumeindegepland">Datumeindegepland</span>
          </dt>
          <dd>{zaakorigineelEntity.datumeindegepland}</dd>
          <dt>
            <span id="datumeindeuiterlijkeafdoening">Datumeindeuiterlijkeafdoening</span>
          </dt>
          <dd>{zaakorigineelEntity.datumeindeuiterlijkeafdoening}</dd>
          <dt>
            <span id="datumlaatstebetaling">Datumlaatstebetaling</span>
          </dt>
          <dd>{zaakorigineelEntity.datumlaatstebetaling}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{zaakorigineelEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumregistratie">Datumregistratie</span>
          </dt>
          <dd>{zaakorigineelEntity.datumregistratie}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{zaakorigineelEntity.datumstart}</dd>
          <dt>
            <span id="datumvernietigingdossier">Datumvernietigingdossier</span>
          </dt>
          <dd>{zaakorigineelEntity.datumvernietigingdossier}</dd>
          <dt>
            <span id="indicatiebetaling">Indicatiebetaling</span>
          </dt>
          <dd>{zaakorigineelEntity.indicatiebetaling}</dd>
          <dt>
            <span id="indicatiedeelzaken">Indicatiedeelzaken</span>
          </dt>
          <dd>{zaakorigineelEntity.indicatiedeelzaken}</dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{zaakorigineelEntity.kenmerk}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{zaakorigineelEntity.omschrijving}</dd>
          <dt>
            <span id="omschrijvingresultaat">Omschrijvingresultaat</span>
          </dt>
          <dd>{zaakorigineelEntity.omschrijvingresultaat}</dd>
          <dt>
            <span id="opschorting">Opschorting</span>
          </dt>
          <dd>{zaakorigineelEntity.opschorting}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{zaakorigineelEntity.toelichting}</dd>
          <dt>
            <span id="toelichtingresultaat">Toelichtingresultaat</span>
          </dt>
          <dd>{zaakorigineelEntity.toelichtingresultaat}</dd>
          <dt>
            <span id="verlenging">Verlenging</span>
          </dt>
          <dd>{zaakorigineelEntity.verlenging}</dd>
          <dt>
            <span id="zaakidentificatie">Zaakidentificatie</span>
          </dt>
          <dd>{zaakorigineelEntity.zaakidentificatie}</dd>
          <dt>
            <span id="zaakniveau">Zaakniveau</span>
          </dt>
          <dd>{zaakorigineelEntity.zaakniveau}</dd>
        </dl>
        <Button tag={Link} to="/zaakorigineel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zaakorigineel/${zaakorigineelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZaakorigineelDetail;
