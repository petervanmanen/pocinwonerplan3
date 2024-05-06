import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './balieverkoop.reducer';

export const BalieverkoopDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const balieverkoopEntity = useAppSelector(state => state.balieverkoop.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="balieverkoopDetailsHeading">Balieverkoop</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{balieverkoopEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{balieverkoopEntity.aantal}</dd>
          <dt>
            <span id="kanaal">Kanaal</span>
          </dt>
          <dd>{balieverkoopEntity.kanaal}</dd>
          <dt>
            <span id="verkooptijd">Verkooptijd</span>
          </dt>
          <dd>{balieverkoopEntity.verkooptijd}</dd>
          <dt>Tegenprijs Prijs</dt>
          <dd>{balieverkoopEntity.tegenprijsPrijs ? balieverkoopEntity.tegenprijsPrijs.id : ''}</dd>
          <dt>Betreft Product</dt>
          <dd>{balieverkoopEntity.betreftProduct ? balieverkoopEntity.betreftProduct.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/balieverkoop" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/balieverkoop/${balieverkoopEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BalieverkoopDetail;
