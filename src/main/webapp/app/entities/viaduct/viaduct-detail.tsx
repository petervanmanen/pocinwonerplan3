import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './viaduct.reducer';

export const ViaductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const viaductEntity = useAppSelector(state => state.viaduct.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="viaductDetailsHeading">Viaduct</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{viaductEntity.id}</dd>
          <dt>
            <span id="aantaloverspanningen">Aantaloverspanningen</span>
          </dt>
          <dd>{viaductEntity.aantaloverspanningen}</dd>
          <dt>
            <span id="belastingklassenieuw">Belastingklassenieuw</span>
          </dt>
          <dd>{viaductEntity.belastingklassenieuw}</dd>
          <dt>
            <span id="belastingklasseoud">Belastingklasseoud</span>
          </dt>
          <dd>{viaductEntity.belastingklasseoud}</dd>
          <dt>
            <span id="draagvermogen">Draagvermogen</span>
          </dt>
          <dd>{viaductEntity.draagvermogen}</dd>
          <dt>
            <span id="maximaaltoelaatbaarvoertuiggewicht">Maximaaltoelaatbaarvoertuiggewicht</span>
          </dt>
          <dd>{viaductEntity.maximaaltoelaatbaarvoertuiggewicht}</dd>
          <dt>
            <span id="maximaleasbelasting">Maximaleasbelasting</span>
          </dt>
          <dd>{viaductEntity.maximaleasbelasting}</dd>
          <dt>
            <span id="maximaleoverspanning">Maximaleoverspanning</span>
          </dt>
          <dd>{viaductEntity.maximaleoverspanning}</dd>
          <dt>
            <span id="overbruggingsobjectdoorrijopening">Overbruggingsobjectdoorrijopening</span>
          </dt>
          <dd>{viaductEntity.overbruggingsobjectdoorrijopening}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{viaductEntity.type}</dd>
          <dt>
            <span id="waterobject">Waterobject</span>
          </dt>
          <dd>{viaductEntity.waterobject}</dd>
          <dt>
            <span id="zwaarstevoertuig">Zwaarstevoertuig</span>
          </dt>
          <dd>{viaductEntity.zwaarstevoertuig}</dd>
        </dl>
        <Button tag={Link} to="/viaduct" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/viaduct/${viaductEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ViaductDetail;
