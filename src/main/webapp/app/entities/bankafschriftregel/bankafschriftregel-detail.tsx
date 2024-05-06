import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bankafschriftregel.reducer';

export const BankafschriftregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankafschriftregelEntity = useAppSelector(state => state.bankafschriftregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankafschriftregelDetailsHeading">Bankafschriftregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bankafschriftregelEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{bankafschriftregelEntity.bedrag}</dd>
          <dt>
            <span id="bij">Bij</span>
          </dt>
          <dd>{bankafschriftregelEntity.bij ? 'true' : 'false'}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{bankafschriftregelEntity.datum}</dd>
          <dt>
            <span id="rekeningvan">Rekeningvan</span>
          </dt>
          <dd>{bankafschriftregelEntity.rekeningvan}</dd>
          <dt>Leidttot Mutatie</dt>
          <dd>{bankafschriftregelEntity.leidttotMutatie ? bankafschriftregelEntity.leidttotMutatie.id : ''}</dd>
          <dt>Heeft Bankafschrift</dt>
          <dd>{bankafschriftregelEntity.heeftBankafschrift ? bankafschriftregelEntity.heeftBankafschrift.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bankafschriftregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bankafschriftregel/${bankafschriftregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankafschriftregelDetail;
