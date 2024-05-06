import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './flyover.reducer';

export const FlyoverDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const flyoverEntity = useAppSelector(state => state.flyover.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="flyoverDetailsHeading">Flyover</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{flyoverEntity.id}</dd>
          <dt>
            <span id="aantaloverspanningen">Aantaloverspanningen</span>
          </dt>
          <dd>{flyoverEntity.aantaloverspanningen}</dd>
          <dt>
            <span id="belastingklassenieuw">Belastingklassenieuw</span>
          </dt>
          <dd>{flyoverEntity.belastingklassenieuw}</dd>
          <dt>
            <span id="belastingklasseoud">Belastingklasseoud</span>
          </dt>
          <dd>{flyoverEntity.belastingklasseoud}</dd>
          <dt>
            <span id="draagvermogen">Draagvermogen</span>
          </dt>
          <dd>{flyoverEntity.draagvermogen}</dd>
          <dt>
            <span id="maximaaltoelaatbaarvoertuiggewicht">Maximaaltoelaatbaarvoertuiggewicht</span>
          </dt>
          <dd>{flyoverEntity.maximaaltoelaatbaarvoertuiggewicht}</dd>
          <dt>
            <span id="maximaleasbelasting">Maximaleasbelasting</span>
          </dt>
          <dd>{flyoverEntity.maximaleasbelasting}</dd>
          <dt>
            <span id="maximaleoverspanning">Maximaleoverspanning</span>
          </dt>
          <dd>{flyoverEntity.maximaleoverspanning}</dd>
          <dt>
            <span id="overbruggingsobjectdoorrijopening">Overbruggingsobjectdoorrijopening</span>
          </dt>
          <dd>{flyoverEntity.overbruggingsobjectdoorrijopening}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{flyoverEntity.type}</dd>
          <dt>
            <span id="zwaarstevoertuig">Zwaarstevoertuig</span>
          </dt>
          <dd>{flyoverEntity.zwaarstevoertuig}</dd>
        </dl>
        <Button tag={Link} to="/flyover" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/flyover/${flyoverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FlyoverDetail;
