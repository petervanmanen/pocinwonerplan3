import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lijn.reducer';

export const LijnDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lijnEntity = useAppSelector(state => state.lijn.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lijnDetailsHeading">Lijn</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{lijnEntity.id}</dd>
          <dt>
            <span id="lijn">Lijn</span>
          </dt>
          <dd>{lijnEntity.lijn}</dd>
          <dt>Omvat Lijnengroep</dt>
          <dd>{lijnEntity.omvatLijnengroep ? lijnEntity.omvatLijnengroep.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lijn" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lijn/${lijnEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LijnDetail;
