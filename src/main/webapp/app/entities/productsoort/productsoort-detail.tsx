import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './productsoort.reducer';

export const ProductsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productsoortEntity = useAppSelector(state => state.productsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productsoortDetailsHeading">Productsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productsoortEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{productsoortEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{productsoortEntity.omschrijving}</dd>
          <dt>
            <span id="tarief">Tarief</span>
          </dt>
          <dd>{productsoortEntity.tarief}</dd>
          <dt>
            <span id="tariefperiode">Tariefperiode</span>
          </dt>
          <dd>{productsoortEntity.tariefperiode}</dd>
          <dt>Valtbinnen Productgroep</dt>
          <dd>{productsoortEntity.valtbinnenProductgroep ? productsoortEntity.valtbinnenProductgroep.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/productsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/productsoort/${productsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductsoortDetail;
