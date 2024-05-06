import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecoduct.reducer';

export const EcoductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ecoductEntity = useAppSelector(state => state.ecoduct.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecoductDetailsHeading">Ecoduct</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ecoductEntity.id}</dd>
          <dt>
            <span id="aantaloverspanningen">Aantaloverspanningen</span>
          </dt>
          <dd>{ecoductEntity.aantaloverspanningen}</dd>
          <dt>
            <span id="draagvermogen">Draagvermogen</span>
          </dt>
          <dd>{ecoductEntity.draagvermogen}</dd>
          <dt>
            <span id="maximaaltoelaatbaarvoertuiggewicht">Maximaaltoelaatbaarvoertuiggewicht</span>
          </dt>
          <dd>{ecoductEntity.maximaaltoelaatbaarvoertuiggewicht}</dd>
          <dt>
            <span id="maximaleasbelasting">Maximaleasbelasting</span>
          </dt>
          <dd>{ecoductEntity.maximaleasbelasting}</dd>
          <dt>
            <span id="maximaleoverspanning">Maximaleoverspanning</span>
          </dt>
          <dd>{ecoductEntity.maximaleoverspanning}</dd>
          <dt>
            <span id="overbruggingsobjectdoorrijopening">Overbruggingsobjectdoorrijopening</span>
          </dt>
          <dd>{ecoductEntity.overbruggingsobjectdoorrijopening}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{ecoductEntity.type}</dd>
          <dt>
            <span id="zwaarstevoertuig">Zwaarstevoertuig</span>
          </dt>
          <dd>{ecoductEntity.zwaarstevoertuig}</dd>
        </dl>
        <Button tag={Link} to="/ecoduct" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ecoduct/${ecoductEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcoductDetail;
