import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bankafschrift.reducer';

export const BankafschriftDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankafschriftEntity = useAppSelector(state => state.bankafschrift.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankafschriftDetailsHeading">Bankafschrift</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bankafschriftEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{bankafschriftEntity.datum}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{bankafschriftEntity.nummer}</dd>
          <dt>Heeft Bankrekening</dt>
          <dd>{bankafschriftEntity.heeftBankrekening ? bankafschriftEntity.heeftBankrekening.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bankafschrift" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bankafschrift/${bankafschriftEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankafschriftDetail;
