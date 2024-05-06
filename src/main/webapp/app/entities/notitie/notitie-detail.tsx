import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notitie.reducer';

export const NotitieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notitieEntity = useAppSelector(state => state.notitie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notitieDetailsHeading">Notitie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notitieEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{notitieEntity.datum ? <TextFormat value={notitieEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="inhoud">Inhoud</span>
          </dt>
          <dd>{notitieEntity.inhoud}</dd>
          <dt>Auteur Medewerker</dt>
          <dd>{notitieEntity.auteurMedewerker ? notitieEntity.auteurMedewerker.id : ''}</dd>
          <dt>Heeftnotities Applicatie</dt>
          <dd>{notitieEntity.heeftnotitiesApplicatie ? notitieEntity.heeftnotitiesApplicatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notitie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notitie/${notitieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotitieDetail;
