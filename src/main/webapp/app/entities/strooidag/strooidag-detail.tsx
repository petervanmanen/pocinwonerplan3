import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './strooidag.reducer';

export const StrooidagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const strooidagEntity = useAppSelector(state => state.strooidag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strooidagDetailsHeading">Strooidag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{strooidagEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{strooidagEntity.datum ? <TextFormat value={strooidagEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="maximumtemperatuur">Maximumtemperatuur</span>
          </dt>
          <dd>{strooidagEntity.maximumtemperatuur}</dd>
          <dt>
            <span id="minimumtemperatuur">Minimumtemperatuur</span>
          </dt>
          <dd>{strooidagEntity.minimumtemperatuur}</dd>
          <dt>
            <span id="tijdmaximumtemperatuur">Tijdmaximumtemperatuur</span>
          </dt>
          <dd>{strooidagEntity.tijdmaximumtemperatuur}</dd>
          <dt>
            <span id="tijdminimumtemperatuur">Tijdminimumtemperatuur</span>
          </dt>
          <dd>{strooidagEntity.tijdminimumtemperatuur}</dd>
        </dl>
        <Button tag={Link} to="/strooidag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strooidag/${strooidagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrooidagDetail;
