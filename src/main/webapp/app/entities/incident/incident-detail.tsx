import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './incident.reducer';

export const IncidentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const incidentEntity = useAppSelector(state => state.incident.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="incidentDetailsHeading">Incident</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{incidentEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{incidentEntity.datum ? <TextFormat value={incidentEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{incidentEntity.locatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{incidentEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{incidentEntity.omschrijving}</dd>
          <dt>Betreft Museumobject</dt>
          <dd>
            {incidentEntity.betreftMuseumobjects
              ? incidentEntity.betreftMuseumobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {incidentEntity.betreftMuseumobjects && i === incidentEntity.betreftMuseumobjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/incident" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/incident/${incidentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IncidentDetail;
