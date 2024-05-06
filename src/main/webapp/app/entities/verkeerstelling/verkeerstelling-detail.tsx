import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verkeerstelling.reducer';

export const VerkeerstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verkeerstellingEntity = useAppSelector(state => state.verkeerstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verkeerstellingDetailsHeading">Verkeerstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verkeerstellingEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{verkeerstellingEntity.aantal}</dd>
          <dt>
            <span id="tijdtot">Tijdtot</span>
          </dt>
          <dd>{verkeerstellingEntity.tijdtot}</dd>
          <dt>
            <span id="tijdvanaf">Tijdvanaf</span>
          </dt>
          <dd>{verkeerstellingEntity.tijdvanaf}</dd>
          <dt>Gegenereerddoor Sensor</dt>
          <dd>{verkeerstellingEntity.gegenereerddoorSensor ? verkeerstellingEntity.gegenereerddoorSensor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verkeerstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verkeerstelling/${verkeerstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerkeerstellingDetail;
