import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './factuurregel.reducer';

export const FactuurregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const factuurregelEntity = useAppSelector(state => state.factuurregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="factuurregelDetailsHeading">Factuurregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{factuurregelEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{factuurregelEntity.aantal}</dd>
          <dt>
            <span id="bedragbtw">Bedragbtw</span>
          </dt>
          <dd>{factuurregelEntity.bedragbtw}</dd>
          <dt>
            <span id="bedragexbtw">Bedragexbtw</span>
          </dt>
          <dd>{factuurregelEntity.bedragexbtw}</dd>
          <dt>
            <span id="btwpercentage">Btwpercentage</span>
          </dt>
          <dd>{factuurregelEntity.btwpercentage}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{factuurregelEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{factuurregelEntity.omschrijving}</dd>
          <dt>Leidttot Mutatie</dt>
          <dd>{factuurregelEntity.leidttotMutatie ? factuurregelEntity.leidttotMutatie.id : ''}</dd>
          <dt>Heeft Factuur</dt>
          <dd>{factuurregelEntity.heeftFactuur ? factuurregelEntity.heeftFactuur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/factuurregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/factuurregel/${factuurregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FactuurregelDetail;
