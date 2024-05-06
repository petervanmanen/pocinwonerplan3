import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './taak.reducer';

export const TaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taakEntity = useAppSelector(state => state.taak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taakDetailsHeading">Taak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{taakEntity.id}</dd>
          <dt>Projectleider Rechtspersoon</dt>
          <dd>{taakEntity.projectleiderRechtspersoon ? taakEntity.projectleiderRechtspersoon.id : ''}</dd>
          <dt>Heeft Subsidie</dt>
          <dd>{taakEntity.heeftSubsidie ? taakEntity.heeftSubsidie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/taak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/taak/${taakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaakDetail;
