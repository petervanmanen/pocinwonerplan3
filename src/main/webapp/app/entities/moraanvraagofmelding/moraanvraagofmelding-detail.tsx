import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './moraanvraagofmelding.reducer';

export const MoraanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const moraanvraagofmeldingEntity = useAppSelector(state => state.moraanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="moraanvraagofmeldingDetailsHeading">Moraanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="crow">Crow</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.crow}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.locatie}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.locatieomschrijving}</dd>
          <dt>
            <span id="meldingomschrijving">Meldingomschrijving</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.meldingomschrijving}</dd>
          <dt>
            <span id="meldingtekst">Meldingtekst</span>
          </dt>
          <dd>{moraanvraagofmeldingEntity.meldingtekst}</dd>
        </dl>
        <Button tag={Link} to="/moraanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/moraanvraagofmelding/${moraanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MoraanvraagofmeldingDetail;
