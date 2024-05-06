import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stelling.reducer';

export const StellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stellingEntity = useAppSelector(state => state.stelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stellingDetailsHeading">Stelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stellingEntity.id}</dd>
          <dt>
            <span id="inhoud">Inhoud</span>
          </dt>
          <dd>{stellingEntity.inhoud}</dd>
          <dt>
            <span id="stellingcode">Stellingcode</span>
          </dt>
          <dd>{stellingEntity.stellingcode}</dd>
          <dt>Heeft Depot</dt>
          <dd>{stellingEntity.heeftDepot ? stellingEntity.heeftDepot.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stelling/${stellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StellingDetail;
