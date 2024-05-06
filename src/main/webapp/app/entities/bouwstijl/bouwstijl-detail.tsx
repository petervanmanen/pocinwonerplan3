import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwstijl.reducer';

export const BouwstijlDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwstijlEntity = useAppSelector(state => state.bouwstijl.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwstijlDetailsHeading">Bouwstijl</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwstijlEntity.id}</dd>
          <dt>
            <span id="hoofdstijl">Hoofdstijl</span>
          </dt>
          <dd>{bouwstijlEntity.hoofdstijl}</dd>
          <dt>
            <span id="substijl">Substijl</span>
          </dt>
          <dd>{bouwstijlEntity.substijl}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{bouwstijlEntity.toelichting}</dd>
          <dt>
            <span id="zuiverheid">Zuiverheid</span>
          </dt>
          <dd>{bouwstijlEntity.zuiverheid}</dd>
        </dl>
        <Button tag={Link} to="/bouwstijl" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwstijl/${bouwstijlEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwstijlDetail;
