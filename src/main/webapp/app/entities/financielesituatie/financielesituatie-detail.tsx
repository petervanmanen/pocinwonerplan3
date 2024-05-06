import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './financielesituatie.reducer';

export const FinancielesituatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const financielesituatieEntity = useAppSelector(state => state.financielesituatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="financielesituatieDetailsHeading">Financielesituatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{financielesituatieEntity.id}</dd>
          <dt>
            <span id="datumvastgesteld">Datumvastgesteld</span>
          </dt>
          <dd>{financielesituatieEntity.datumvastgesteld}</dd>
          <dt>
            <span id="schuld">Schuld</span>
          </dt>
          <dd>{financielesituatieEntity.schuld}</dd>
        </dl>
        <Button tag={Link} to="/financielesituatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/financielesituatie/${financielesituatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FinancielesituatieDetail;
