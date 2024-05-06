import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cpvcode.reducer';

export const CpvcodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cpvcodeEntity = useAppSelector(state => state.cpvcode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cpvcodeDetailsHeading">Cpvcode</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cpvcodeEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{cpvcodeEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{cpvcodeEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/cpvcode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cpvcode/${cpvcodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CpvcodeDetail;
