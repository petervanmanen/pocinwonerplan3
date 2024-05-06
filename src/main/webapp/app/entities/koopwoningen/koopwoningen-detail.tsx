import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './koopwoningen.reducer';

export const KoopwoningenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const koopwoningenEntity = useAppSelector(state => state.koopwoningen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="koopwoningenDetailsHeading">Koopwoningen</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{koopwoningenEntity.id}</dd>
          <dt>
            <span id="koopprijs">Koopprijs</span>
          </dt>
          <dd>{koopwoningenEntity.koopprijs}</dd>
        </dl>
        <Button tag={Link} to="/koopwoningen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/koopwoningen/${koopwoningenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KoopwoningenDetail;
