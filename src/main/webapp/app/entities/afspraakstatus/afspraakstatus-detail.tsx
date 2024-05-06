import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './afspraakstatus.reducer';

export const AfspraakstatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const afspraakstatusEntity = useAppSelector(state => state.afspraakstatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="afspraakstatusDetailsHeading">Afspraakstatus</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{afspraakstatusEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{afspraakstatusEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/afspraakstatus" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/afspraakstatus/${afspraakstatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AfspraakstatusDetail;
