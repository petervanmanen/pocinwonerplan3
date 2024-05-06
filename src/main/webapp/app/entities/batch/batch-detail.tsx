import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './batch.reducer';

export const BatchDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const batchEntity = useAppSelector(state => state.batch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="batchDetailsHeading">Batch</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{batchEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{batchEntity.datum}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{batchEntity.nummer}</dd>
          <dt>
            <span id="tijd">Tijd</span>
          </dt>
          <dd>{batchEntity.tijd}</dd>
          <dt>Heeftherkomst Applicatie</dt>
          <dd>{batchEntity.heeftherkomstApplicatie ? batchEntity.heeftherkomstApplicatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/batch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/batch/${batchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BatchDetail;
