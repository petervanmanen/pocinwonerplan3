import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservering.reducer';

export const ReserveringDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reserveringEntity = useAppSelector(state => state.reservering.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reserveringDetailsHeading">Reservering</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reserveringEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{reserveringEntity.aantal}</dd>
          <dt>
            <span id="btw">Btw</span>
          </dt>
          <dd>{reserveringEntity.btw}</dd>
          <dt>
            <span id="tijdtot">Tijdtot</span>
          </dt>
          <dd>{reserveringEntity.tijdtot}</dd>
          <dt>
            <span id="tijdvanaf">Tijdvanaf</span>
          </dt>
          <dd>{reserveringEntity.tijdvanaf}</dd>
          <dt>
            <span id="totaalprijs">Totaalprijs</span>
          </dt>
          <dd>{reserveringEntity.totaalprijs}</dd>
          <dt>Betreft Voorziening</dt>
          <dd>{reserveringEntity.betreftVoorziening ? reserveringEntity.betreftVoorziening.id : ''}</dd>
          <dt>Betreft Zaal</dt>
          <dd>{reserveringEntity.betreftZaal ? reserveringEntity.betreftZaal.id : ''}</dd>
          <dt>Heeft Activiteit</dt>
          <dd>{reserveringEntity.heeftActiviteit ? reserveringEntity.heeftActiviteit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reservering" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservering/${reserveringEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReserveringDetail;
