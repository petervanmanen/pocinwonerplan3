import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voertuig.reducer';

export const VoertuigDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voertuigEntity = useAppSelector(state => state.voertuig.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voertuigDetailsHeading">Voertuig</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{voertuigEntity.id}</dd>
          <dt>
            <span id="kenteken">Kenteken</span>
          </dt>
          <dd>{voertuigEntity.kenteken}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{voertuigEntity.kleur}</dd>
          <dt>
            <span id="land">Land</span>
          </dt>
          <dd>{voertuigEntity.land}</dd>
          <dt>
            <span id="merk">Merk</span>
          </dt>
          <dd>{voertuigEntity.merk}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{voertuigEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/voertuig" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voertuig/${voertuigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoertuigDetail;
