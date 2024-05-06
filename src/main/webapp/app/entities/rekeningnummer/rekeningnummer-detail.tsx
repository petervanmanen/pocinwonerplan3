import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rekeningnummer.reducer';

export const RekeningnummerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rekeningnummerEntity = useAppSelector(state => state.rekeningnummer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rekeningnummerDetailsHeading">Rekeningnummer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rekeningnummerEntity.id}</dd>
          <dt>
            <span id="bic">Bic</span>
          </dt>
          <dd>{rekeningnummerEntity.bic}</dd>
          <dt>
            <span id="iban">Iban</span>
          </dt>
          <dd>{rekeningnummerEntity.iban}</dd>
        </dl>
        <Button tag={Link} to="/rekeningnummer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rekeningnummer/${rekeningnummerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RekeningnummerDetail;
