import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './koppeling.reducer';

export const KoppelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const koppelingEntity = useAppSelector(state => state.koppeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="koppelingDetailsHeading">Koppeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{koppelingEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{koppelingEntity.beschrijving}</dd>
          <dt>
            <span id="direct">Direct</span>
          </dt>
          <dd>{koppelingEntity.direct ? 'true' : 'false'}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{koppelingEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/koppeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/koppeling/${koppelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KoppelingDetail;
