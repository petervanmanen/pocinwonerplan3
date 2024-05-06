import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './omzetgroep.reducer';

export const OmzetgroepDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const omzetgroepEntity = useAppSelector(state => state.omzetgroep.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="omzetgroepDetailsHeading">Omzetgroep</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{omzetgroepEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{omzetgroepEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{omzetgroepEntity.omschrijving}</dd>
          <dt>Valtbinnen Product</dt>
          <dd>
            {omzetgroepEntity.valtbinnenProducts
              ? omzetgroepEntity.valtbinnenProducts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {omzetgroepEntity.valtbinnenProducts && i === omzetgroepEntity.valtbinnenProducts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/omzetgroep" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/omzetgroep/${omzetgroepEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OmzetgroepDetail;
