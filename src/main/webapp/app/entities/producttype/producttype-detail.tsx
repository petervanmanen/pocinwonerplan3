import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producttype.reducer';

export const ProducttypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const producttypeEntity = useAppSelector(state => state.producttype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="producttypeDetailsHeading">Producttype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{producttypeEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{producttypeEntity.omschrijving}</dd>
          <dt>Heeft Bedrijfsprocestype</dt>
          <dd>{producttypeEntity.heeftBedrijfsprocestype ? producttypeEntity.heeftBedrijfsprocestype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producttype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producttype/${producttypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProducttypeDetail;
