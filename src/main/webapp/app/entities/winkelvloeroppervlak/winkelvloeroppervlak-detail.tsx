import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './winkelvloeroppervlak.reducer';

export const WinkelvloeroppervlakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const winkelvloeroppervlakEntity = useAppSelector(state => state.winkelvloeroppervlak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="winkelvloeroppervlakDetailsHeading">Winkelvloeroppervlak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.id}</dd>
          <dt>
            <span id="aantalkassa">Aantalkassa</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.aantalkassa}</dd>
          <dt>
            <span id="bronwvo">Bronwvo</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.bronwvo}</dd>
          <dt>
            <span id="leegstand">Leegstand</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.leegstand}</dd>
          <dt>
            <span id="winkelvloeroppervlakte">Winkelvloeroppervlakte</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.winkelvloeroppervlakte}</dd>
          <dt>
            <span id="wvoklasse">Wvoklasse</span>
          </dt>
          <dd>{winkelvloeroppervlakEntity.wvoklasse}</dd>
        </dl>
        <Button tag={Link} to="/winkelvloeroppervlak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/winkelvloeroppervlak/${winkelvloeroppervlakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WinkelvloeroppervlakDetail;
