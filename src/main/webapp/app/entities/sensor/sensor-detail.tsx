import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sensor.reducer';

export const SensorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sensorEntity = useAppSelector(state => state.sensor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sensorDetailsHeading">Sensor</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sensorEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{sensorEntity.aanleghoogte}</dd>
          <dt>
            <span id="elektrakast">Elektrakast</span>
          </dt>
          <dd>{sensorEntity.elektrakast}</dd>
          <dt>
            <span id="frequentieomvormer">Frequentieomvormer</span>
          </dt>
          <dd>{sensorEntity.frequentieomvormer}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{sensorEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{sensorEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{sensorEntity.leverancier}</dd>
          <dt>
            <span id="meetpunt">Meetpunt</span>
          </dt>
          <dd>{sensorEntity.meetpunt}</dd>
          <dt>
            <span id="plc">Plc</span>
          </dt>
          <dd>{sensorEntity.plc}</dd>
        </dl>
        <Button tag={Link} to="/sensor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sensor/${sensorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SensorDetail;
