import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verlengingzaak.reducer';

export const VerlengingzaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verlengingzaakEntity = useAppSelector(state => state.verlengingzaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verlengingzaakDetailsHeading">Verlengingzaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verlengingzaakEntity.id}</dd>
          <dt>
            <span id="duurverlenging">Duurverlenging</span>
          </dt>
          <dd>{verlengingzaakEntity.duurverlenging}</dd>
          <dt>
            <span id="redenverlenging">Redenverlenging</span>
          </dt>
          <dd>{verlengingzaakEntity.redenverlenging}</dd>
        </dl>
        <Button tag={Link} to="/verlengingzaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verlengingzaak/${verlengingzaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerlengingzaakDetail;
