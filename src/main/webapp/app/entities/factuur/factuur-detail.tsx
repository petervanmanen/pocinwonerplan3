import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './factuur.reducer';

export const FactuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const factuurEntity = useAppSelector(state => state.factuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="factuurDetailsHeading">Factuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{factuurEntity.id}</dd>
          <dt>
            <span id="betaalbaarper">Betaalbaarper</span>
          </dt>
          <dd>{factuurEntity.betaalbaarper}</dd>
          <dt>
            <span id="betaaltermijn">Betaaltermijn</span>
          </dt>
          <dd>{factuurEntity.betaaltermijn}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{factuurEntity.code}</dd>
          <dt>
            <span id="datumfactuur">Datumfactuur</span>
          </dt>
          <dd>{factuurEntity.datumfactuur}</dd>
          <dt>
            <span id="factuurbedragbtw">Factuurbedragbtw</span>
          </dt>
          <dd>{factuurEntity.factuurbedragbtw}</dd>
          <dt>
            <span id="factuurbedragexclusiefbtw">Factuurbedragexclusiefbtw</span>
          </dt>
          <dd>{factuurEntity.factuurbedragexclusiefbtw}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{factuurEntity.omschrijving}</dd>
          <dt>Schrijftop Kostenplaats</dt>
          <dd>{factuurEntity.schrijftopKostenplaats ? factuurEntity.schrijftopKostenplaats.id : ''}</dd>
          <dt>Gedektvia Inkooporder</dt>
          <dd>{factuurEntity.gedektviaInkooporder ? factuurEntity.gedektviaInkooporder.id : ''}</dd>
          <dt>Crediteur Leverancier</dt>
          <dd>{factuurEntity.crediteurLeverancier ? factuurEntity.crediteurLeverancier.id : ''}</dd>
          <dt>Heeft Debiteur</dt>
          <dd>{factuurEntity.heeftDebiteur ? factuurEntity.heeftDebiteur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/factuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/factuur/${factuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FactuurDetail;
