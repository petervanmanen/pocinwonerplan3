import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bankrekening.reducer';

export const BankrekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankrekeningEntity = useAppSelector(state => state.bankrekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankrekeningDetailsHeading">Bankrekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bankrekeningEntity.id}</dd>
          <dt>
            <span id="bank">Bank</span>
          </dt>
          <dd>{bankrekeningEntity.bank}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{bankrekeningEntity.nummer}</dd>
          <dt>
            <span id="tennaamstelling">Tennaamstelling</span>
          </dt>
          <dd>{bankrekeningEntity.tennaamstelling}</dd>
        </dl>
        <Button tag={Link} to="/bankrekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bankrekening/${bankrekeningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankrekeningDetail;
