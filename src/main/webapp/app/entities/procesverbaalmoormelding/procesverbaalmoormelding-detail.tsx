import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './procesverbaalmoormelding.reducer';

export const ProcesverbaalmoormeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const procesverbaalmoormeldingEntity = useAppSelector(state => state.procesverbaalmoormelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="procesverbaalmoormeldingDetailsHeading">Procesverbaalmoormelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{procesverbaalmoormeldingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{procesverbaalmoormeldingEntity.datum}</dd>
          <dt>
            <span id="goedkeuring">Goedkeuring</span>
          </dt>
          <dd>{procesverbaalmoormeldingEntity.goedkeuring ? 'true' : 'false'}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{procesverbaalmoormeldingEntity.opmerkingen}</dd>
        </dl>
        <Button tag={Link} to="/procesverbaalmoormelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/procesverbaalmoormelding/${procesverbaalmoormeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProcesverbaalmoormeldingDetail;
