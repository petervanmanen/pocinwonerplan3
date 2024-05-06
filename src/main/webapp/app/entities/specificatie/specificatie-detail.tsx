import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './specificatie.reducer';

export const SpecificatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const specificatieEntity = useAppSelector(state => state.specificatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="specificatieDetailsHeading">Specificatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{specificatieEntity.id}</dd>
          <dt>
            <span id="antwoord">Antwoord</span>
          </dt>
          <dd>{specificatieEntity.antwoord}</dd>
          <dt>
            <span id="groepering">Groepering</span>
          </dt>
          <dd>{specificatieEntity.groepering}</dd>
          <dt>
            <span id="publiceerbaar">Publiceerbaar</span>
          </dt>
          <dd>{specificatieEntity.publiceerbaar}</dd>
          <dt>
            <span id="vraagclassificatie">Vraagclassificatie</span>
          </dt>
          <dd>{specificatieEntity.vraagclassificatie}</dd>
          <dt>
            <span id="vraagid">Vraagid</span>
          </dt>
          <dd>{specificatieEntity.vraagid}</dd>
          <dt>
            <span id="vraagreferentie">Vraagreferentie</span>
          </dt>
          <dd>{specificatieEntity.vraagreferentie}</dd>
          <dt>
            <span id="vraagtekst">Vraagtekst</span>
          </dt>
          <dd>{specificatieEntity.vraagtekst}</dd>
          <dt>Gedefinieerddoor Projectactiviteit</dt>
          <dd>{specificatieEntity.gedefinieerddoorProjectactiviteit ? specificatieEntity.gedefinieerddoorProjectactiviteit.id : ''}</dd>
          <dt>Bevat Verzoek</dt>
          <dd>{specificatieEntity.bevatVerzoek ? specificatieEntity.bevatVerzoek.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/specificatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/specificatie/${specificatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpecificatieDetail;
