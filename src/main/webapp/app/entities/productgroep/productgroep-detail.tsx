import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './productgroep.reducer';

export const ProductgroepDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productgroepEntity = useAppSelector(state => state.productgroep.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productgroepDetailsHeading">Productgroep</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productgroepEntity.id}</dd>
          <dt>
            <span id="beslisboom">Beslisboom</span>
          </dt>
          <dd>{productgroepEntity.beslisboom}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{productgroepEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{productgroepEntity.omschrijving}</dd>
          <dt>Valtbinnen Product</dt>
          <dd>
            {productgroepEntity.valtbinnenProducts
              ? productgroepEntity.valtbinnenProducts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productgroepEntity.valtbinnenProducts && i === productgroepEntity.valtbinnenProducts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/productgroep" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/productgroep/${productgroepEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductgroepDetail;
