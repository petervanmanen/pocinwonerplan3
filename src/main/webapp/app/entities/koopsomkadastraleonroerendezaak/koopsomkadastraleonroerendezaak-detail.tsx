import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './koopsomkadastraleonroerendezaak.reducer';

export const KoopsomkadastraleonroerendezaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const koopsomkadastraleonroerendezaakEntity = useAppSelector(state => state.koopsomkadastraleonroerendezaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="koopsomkadastraleonroerendezaakDetailsHeading">Koopsomkadastraleonroerendezaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{koopsomkadastraleonroerendezaakEntity.id}</dd>
          <dt>
            <span id="datumtransactie">Datumtransactie</span>
          </dt>
          <dd>{koopsomkadastraleonroerendezaakEntity.datumtransactie}</dd>
          <dt>
            <span id="koopsom">Koopsom</span>
          </dt>
          <dd>{koopsomkadastraleonroerendezaakEntity.koopsom}</dd>
        </dl>
        <Button tag={Link} to="/koopsomkadastraleonroerendezaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/koopsomkadastraleonroerendezaak/${koopsomkadastraleonroerendezaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KoopsomkadastraleonroerendezaakDetail;
