import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voorzieningsoort.reducer';

export const VoorzieningsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voorzieningsoortEntity = useAppSelector(state => state.voorzieningsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voorzieningsoortDetailsHeading">Voorzieningsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{voorzieningsoortEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{voorzieningsoortEntity.code}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{voorzieningsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{voorzieningsoortEntity.omschrijving}</dd>
          <dt>
            <span id="productcategorie">Productcategorie</span>
          </dt>
          <dd>{voorzieningsoortEntity.productcategorie}</dd>
          <dt>
            <span id="productcategoriecode">Productcategoriecode</span>
          </dt>
          <dd>{voorzieningsoortEntity.productcategoriecode}</dd>
          <dt>
            <span id="productcode">Productcode</span>
          </dt>
          <dd>{voorzieningsoortEntity.productcode}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{voorzieningsoortEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/voorzieningsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voorzieningsoort/${voorzieningsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoorzieningsoortDetail;
