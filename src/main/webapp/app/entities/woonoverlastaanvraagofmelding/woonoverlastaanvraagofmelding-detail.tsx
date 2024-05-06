import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './woonoverlastaanvraagofmelding.reducer';

export const WoonoverlastaanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const woonoverlastaanvraagofmeldingEntity = useAppSelector(state => state.woonoverlastaanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="woonoverlastaanvraagofmeldingDetailsHeading">Woonoverlastaanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{woonoverlastaanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{woonoverlastaanvraagofmeldingEntity.locatie}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{woonoverlastaanvraagofmeldingEntity.locatieomschrijving}</dd>
          <dt>
            <span id="meldingomschrijving">Meldingomschrijving</span>
          </dt>
          <dd>{woonoverlastaanvraagofmeldingEntity.meldingomschrijving}</dd>
          <dt>
            <span id="meldingtekst">Meldingtekst</span>
          </dt>
          <dd>{woonoverlastaanvraagofmeldingEntity.meldingtekst}</dd>
        </dl>
        <Button tag={Link} to="/woonoverlastaanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/woonoverlastaanvraagofmelding/${woonoverlastaanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WoonoverlastaanvraagofmeldingDetail;
