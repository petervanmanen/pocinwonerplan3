import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vreemdeling.reducer';

export const VreemdelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vreemdelingEntity = useAppSelector(state => state.vreemdeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vreemdelingDetailsHeading">Vreemdeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vreemdelingEntity.id}</dd>
          <dt>
            <span id="sociaalreferent">Sociaalreferent</span>
          </dt>
          <dd>{vreemdelingEntity.sociaalreferent}</dd>
          <dt>
            <span id="vnummer">Vnummer</span>
          </dt>
          <dd>{vreemdelingEntity.vnummer}</dd>
        </dl>
        <Button tag={Link} to="/vreemdeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vreemdeling/${vreemdelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VreemdelingDetail;
