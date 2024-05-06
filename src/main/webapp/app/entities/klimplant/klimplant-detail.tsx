import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './klimplant.reducer';

export const KlimplantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const klimplantEntity = useAppSelector(state => state.klimplant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="klimplantDetailsHeading">Klimplant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{klimplantEntity.id}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{klimplantEntity.hoogte}</dd>
          <dt>
            <span id="knipfrequentie">Knipfrequentie</span>
          </dt>
          <dd>{klimplantEntity.knipfrequentie}</dd>
          <dt>
            <span id="knipoppervlakte">Knipoppervlakte</span>
          </dt>
          <dd>{klimplantEntity.knipoppervlakte}</dd>
          <dt>
            <span id="ondersteuningsvorm">Ondersteuningsvorm</span>
          </dt>
          <dd>{klimplantEntity.ondersteuningsvorm}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{klimplantEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/klimplant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/klimplant/${klimplantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KlimplantDetail;
