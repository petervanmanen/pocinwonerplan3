import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telefoonstatus.reducer';

export const TelefoonstatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telefoonstatusEntity = useAppSelector(state => state.telefoonstatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telefoonstatusDetailsHeading">Telefoonstatus</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{telefoonstatusEntity.id}</dd>
          <dt>
            <span id="contactconnectionstate">Contactconnectionstate</span>
          </dt>
          <dd>{telefoonstatusEntity.contactconnectionstate}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{telefoonstatusEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/telefoonstatus" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telefoonstatus/${telefoonstatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelefoonstatusDetail;
