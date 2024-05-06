import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dienstverband.reducer';

export const DienstverbandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dienstverbandEntity = useAppSelector(state => state.dienstverband.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dienstverbandDetailsHeading">Dienstverband</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dienstverbandEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {dienstverbandEntity.datumeinde ? (
              <TextFormat value={dienstverbandEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {dienstverbandEntity.datumstart ? (
              <TextFormat value={dienstverbandEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="periodiek">Periodiek</span>
          </dt>
          <dd>{dienstverbandEntity.periodiek}</dd>
          <dt>
            <span id="salaris">Salaris</span>
          </dt>
          <dd>{dienstverbandEntity.salaris}</dd>
          <dt>
            <span id="schaal">Schaal</span>
          </dt>
          <dd>{dienstverbandEntity.schaal}</dd>
          <dt>
            <span id="urenperweek">Urenperweek</span>
          </dt>
          <dd>{dienstverbandEntity.urenperweek}</dd>
          <dt>Dienstverbandconformfunctie Functie</dt>
          <dd>{dienstverbandEntity.dienstverbandconformfunctieFunctie ? dienstverbandEntity.dienstverbandconformfunctieFunctie.id : ''}</dd>
          <dt>Aantalvolgensinzet Uren</dt>
          <dd>{dienstverbandEntity.aantalvolgensinzetUren ? dienstverbandEntity.aantalvolgensinzetUren.id : ''}</dd>
          <dt>Medewerkerheeftdienstverband Werknemer</dt>
          <dd>
            {dienstverbandEntity.medewerkerheeftdienstverbandWerknemer ? dienstverbandEntity.medewerkerheeftdienstverbandWerknemer.id : ''}
          </dd>
          <dt>Aantalvolgensinzet Inzet</dt>
          <dd>{dienstverbandEntity.aantalvolgensinzetInzet ? dienstverbandEntity.aantalvolgensinzetInzet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dienstverband" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dienstverband/${dienstverbandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DienstverbandDetail;
