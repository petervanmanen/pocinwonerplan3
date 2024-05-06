import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voorziening.reducer';

export const VoorzieningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voorzieningEntity = useAppSelector(state => state.voorziening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voorzieningDetailsHeading">Voorziening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{voorzieningEntity.id}</dd>
          <dt>
            <span id="aantalbeschikbaar">Aantalbeschikbaar</span>
          </dt>
          <dd>{voorzieningEntity.aantalbeschikbaar}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{voorzieningEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{voorzieningEntity.omschrijving}</dd>
          <dt>Valtbinnen Voorzieningsoort</dt>
          <dd>{voorzieningEntity.valtbinnenVoorzieningsoort ? voorzieningEntity.valtbinnenVoorzieningsoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voorziening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voorziening/${voorzieningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoorzieningDetail;
