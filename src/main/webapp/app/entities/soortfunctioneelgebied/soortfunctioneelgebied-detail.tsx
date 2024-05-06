import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortfunctioneelgebied.reducer';

export const SoortfunctioneelgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortfunctioneelgebiedEntity = useAppSelector(state => state.soortfunctioneelgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortfunctioneelgebiedDetailsHeading">Soortfunctioneelgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortfunctioneelgebiedEntity.id}</dd>
          <dt>
            <span id="indicatieplusbrpopulatie">Indicatieplusbrpopulatie</span>
          </dt>
          <dd>{soortfunctioneelgebiedEntity.indicatieplusbrpopulatie}</dd>
          <dt>
            <span id="typefunctioneelgebied">Typefunctioneelgebied</span>
          </dt>
          <dd>{soortfunctioneelgebiedEntity.typefunctioneelgebied}</dd>
        </dl>
        <Button tag={Link} to="/soortfunctioneelgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortfunctioneelgebied/${soortfunctioneelgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortfunctioneelgebiedDetail;
