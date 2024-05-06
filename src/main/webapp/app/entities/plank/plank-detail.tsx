import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plank.reducer';

export const PlankDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const plankEntity = useAppSelector(state => state.plank.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plankDetailsHeading">Plank</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{plankEntity.id}</dd>
          <dt>
            <span id="planknummer">Planknummer</span>
          </dt>
          <dd>{plankEntity.planknummer}</dd>
          <dt>Heeft Kast</dt>
          <dd>{plankEntity.heeftKast ? plankEntity.heeftKast.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plank" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plank/${plankEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlankDetail;
