import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanbestedinginhuur.reducer';

export const AanbestedinginhuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanbestedinginhuurEntity = useAppSelector(state => state.aanbestedinginhuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanbestedinginhuurDetailsHeading">Aanbestedinginhuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.id}</dd>
          <dt>
            <span id="aanvraaggesloten">Aanvraaggesloten</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.aanvraaggesloten}</dd>
          <dt>
            <span id="aanvraagnummer">Aanvraagnummer</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.aanvraagnummer}</dd>
          <dt>
            <span id="datumcreatie">Datumcreatie</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.datumcreatie}</dd>
          <dt>
            <span id="datumopeningkluis">Datumopeningkluis</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.datumopeningkluis}</dd>
          <dt>
            <span id="datumsluiting">Datumsluiting</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.datumsluiting}</dd>
          <dt>
            <span id="datumverzending">Datumverzending</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.datumverzending}</dd>
          <dt>
            <span id="fase">Fase</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.fase}</dd>
          <dt>
            <span id="hoogstetarief">Hoogstetarief</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.hoogstetarief}</dd>
          <dt>
            <span id="laagstetarief">Laagstetarief</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.laagstetarief}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.omschrijving}</dd>
          <dt>
            <span id="perceel">Perceel</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.perceel}</dd>
          <dt>
            <span id="procedure">Procedure</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.procedure}</dd>
          <dt>
            <span id="projectnaam">Projectnaam</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.projectnaam}</dd>
          <dt>
            <span id="projectreferentie">Projectreferentie</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.projectreferentie}</dd>
          <dt>
            <span id="publicatie">Publicatie</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.publicatie}</dd>
          <dt>
            <span id="referentie">Referentie</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.referentie}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.status}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.titel}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{aanbestedinginhuurEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/aanbestedinginhuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanbestedinginhuur/${aanbestedinginhuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanbestedinginhuurDetail;
