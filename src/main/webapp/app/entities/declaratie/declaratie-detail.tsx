import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './declaratie.reducer';

export const DeclaratieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const declaratieEntity = useAppSelector(state => state.declaratie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="declaratieDetailsHeading">Declaratie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{declaratieEntity.id}</dd>
          <dt>
            <span id="datumdeclaratie">Datumdeclaratie</span>
          </dt>
          <dd>{declaratieEntity.datumdeclaratie}</dd>
          <dt>
            <span id="declaratiebedrag">Declaratiebedrag</span>
          </dt>
          <dd>{declaratieEntity.declaratiebedrag}</dd>
          <dt>
            <span id="declaratiestatus">Declaratiestatus</span>
          </dt>
          <dd>{declaratieEntity.declaratiestatus}</dd>
          <dt>Ingedienddoor Leverancier</dt>
          <dd>{declaratieEntity.ingedienddoorLeverancier ? declaratieEntity.ingedienddoorLeverancier.id : ''}</dd>
          <dt>Soortdeclaratie Declaratiesoort</dt>
          <dd>{declaratieEntity.soortdeclaratieDeclaratiesoort ? declaratieEntity.soortdeclaratieDeclaratiesoort.id : ''}</dd>
          <dt>Dientin Werknemer</dt>
          <dd>{declaratieEntity.dientinWerknemer ? declaratieEntity.dientinWerknemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/declaratie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/declaratie/${declaratieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeclaratieDetail;
