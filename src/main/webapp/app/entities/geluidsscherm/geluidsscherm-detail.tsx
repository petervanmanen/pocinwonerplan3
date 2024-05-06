import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './geluidsscherm.reducer';

export const GeluidsschermDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const geluidsschermEntity = useAppSelector(state => state.geluidsscherm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="geluidsschermDetailsHeading">Geluidsscherm</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{geluidsschermEntity.id}</dd>
          <dt>
            <span id="aantaldeuren">Aantaldeuren</span>
          </dt>
          <dd>{geluidsschermEntity.aantaldeuren}</dd>
          <dt>
            <span id="aantalpanelen">Aantalpanelen</span>
          </dt>
          <dd>{geluidsschermEntity.aantalpanelen}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{geluidsschermEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/geluidsscherm" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/geluidsscherm/${geluidsschermEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeluidsschermDetail;
