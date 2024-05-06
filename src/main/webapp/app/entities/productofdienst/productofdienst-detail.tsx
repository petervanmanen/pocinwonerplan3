import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './productofdienst.reducer';

export const ProductofdienstDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productofdienstEntity = useAppSelector(state => state.productofdienst.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productofdienstDetailsHeading">Productofdienst</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productofdienstEntity.id}</dd>
          <dt>
            <span id="afhandeltijd">Afhandeltijd</span>
          </dt>
          <dd>{productofdienstEntity.afhandeltijd}</dd>
          <dt>
            <span id="ingebruik">Ingebruik</span>
          </dt>
          <dd>{productofdienstEntity.ingebruik}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{productofdienstEntity.naam}</dd>
        </dl>
        <Button tag={Link} to="/productofdienst" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/productofdienst/${productofdienstEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductofdienstDetail;
