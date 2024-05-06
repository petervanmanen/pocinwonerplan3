import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './brug.reducer';

export const BrugDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const brugEntity = useAppSelector(state => state.brug.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="brugDetailsHeading">Brug</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{brugEntity.id}</dd>
          <dt>
            <span id="aantaloverspanningen">Aantaloverspanningen</span>
          </dt>
          <dd>{brugEntity.aantaloverspanningen}</dd>
          <dt>
            <span id="bedienaar">Bedienaar</span>
          </dt>
          <dd>{brugEntity.bedienaar}</dd>
          <dt>
            <span id="bedieningstijden">Bedieningstijden</span>
          </dt>
          <dd>{brugEntity.bedieningstijden}</dd>
          <dt>
            <span id="belastingklassenieuw">Belastingklassenieuw</span>
          </dt>
          <dd>{brugEntity.belastingklassenieuw}</dd>
          <dt>
            <span id="belastingklasseoud">Belastingklasseoud</span>
          </dt>
          <dd>{brugEntity.belastingklasseoud}</dd>
          <dt>
            <span id="beweegbaar">Beweegbaar</span>
          </dt>
          <dd>{brugEntity.beweegbaar ? 'true' : 'false'}</dd>
          <dt>
            <span id="doorrijbreedte">Doorrijbreedte</span>
          </dt>
          <dd>{brugEntity.doorrijbreedte}</dd>
          <dt>
            <span id="draagvermogen">Draagvermogen</span>
          </dt>
          <dd>{brugEntity.draagvermogen}</dd>
          <dt>
            <span id="hoofdroute">Hoofdroute</span>
          </dt>
          <dd>{brugEntity.hoofdroute}</dd>
          <dt>
            <span id="hoofdvaarroute">Hoofdvaarroute</span>
          </dt>
          <dd>{brugEntity.hoofdvaarroute}</dd>
          <dt>
            <span id="maximaaltoelaatbaarvoertuiggewicht">Maximaaltoelaatbaarvoertuiggewicht</span>
          </dt>
          <dd>{brugEntity.maximaaltoelaatbaarvoertuiggewicht}</dd>
          <dt>
            <span id="maximaleasbelasting">Maximaleasbelasting</span>
          </dt>
          <dd>{brugEntity.maximaleasbelasting}</dd>
          <dt>
            <span id="maximaleoverspanning">Maximaleoverspanning</span>
          </dt>
          <dd>{brugEntity.maximaleoverspanning}</dd>
          <dt>
            <span id="statischmoment">Statischmoment</span>
          </dt>
          <dd>{brugEntity.statischmoment}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{brugEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{brugEntity.typeplus}</dd>
          <dt>
            <span id="zwaarstevoertuig">Zwaarstevoertuig</span>
          </dt>
          <dd>{brugEntity.zwaarstevoertuig}</dd>
        </dl>
        <Button tag={Link} to="/brug" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/brug/${brugEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BrugDetail;
