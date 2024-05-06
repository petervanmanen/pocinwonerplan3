import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './balieverkoopentreekaart.reducer';

export const BalieverkoopentreekaartDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const balieverkoopentreekaartEntity = useAppSelector(state => state.balieverkoopentreekaart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="balieverkoopentreekaartDetailsHeading">Balieverkoopentreekaart</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{balieverkoopentreekaartEntity.id}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>{balieverkoopentreekaartEntity.datumeindegeldigheid}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{balieverkoopentreekaartEntity.datumstart}</dd>
          <dt>
            <span id="gebruiktop">Gebruiktop</span>
          </dt>
          <dd>{balieverkoopentreekaartEntity.gebruiktop}</dd>
          <dt>
            <span id="rondleiding">Rondleiding</span>
          </dt>
          <dd>{balieverkoopentreekaartEntity.rondleiding}</dd>
        </dl>
        <Button tag={Link} to="/balieverkoopentreekaart" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/balieverkoopentreekaart/${balieverkoopentreekaartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BalieverkoopentreekaartDetail;
