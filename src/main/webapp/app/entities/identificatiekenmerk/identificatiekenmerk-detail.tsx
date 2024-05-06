import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './identificatiekenmerk.reducer';

export const IdentificatiekenmerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const identificatiekenmerkEntity = useAppSelector(state => state.identificatiekenmerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="identificatiekenmerkDetailsHeading">Identificatiekenmerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{identificatiekenmerkEntity.id}</dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{identificatiekenmerkEntity.kenmerk}</dd>
        </dl>
        <Button tag={Link} to="/identificatiekenmerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/identificatiekenmerk/${identificatiekenmerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdentificatiekenmerkDetail;
