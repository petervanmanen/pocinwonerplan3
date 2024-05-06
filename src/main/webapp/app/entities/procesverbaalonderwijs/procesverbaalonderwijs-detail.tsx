import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './procesverbaalonderwijs.reducer';

export const ProcesverbaalonderwijsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const procesverbaalonderwijsEntity = useAppSelector(state => state.procesverbaalonderwijs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="procesverbaalonderwijsDetailsHeading">Procesverbaalonderwijs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.id}</dd>
          <dt>
            <span id="datumafgehandeld">Datumafgehandeld</span>
          </dt>
          <dd>
            {procesverbaalonderwijsEntity.datumafgehandeld ? (
              <TextFormat value={procesverbaalonderwijsEntity.datumafgehandeld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindeproeftijd">Datumeindeproeftijd</span>
          </dt>
          <dd>
            {procesverbaalonderwijsEntity.datumeindeproeftijd ? (
              <TextFormat value={procesverbaalonderwijsEntity.datumeindeproeftijd} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingelicht">Datumingelicht</span>
          </dt>
          <dd>
            {procesverbaalonderwijsEntity.datumingelicht ? (
              <TextFormat value={procesverbaalonderwijsEntity.datumingelicht} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumuitspraak">Datumuitspraak</span>
          </dt>
          <dd>
            {procesverbaalonderwijsEntity.datumuitspraak ? (
              <TextFormat value={procesverbaalonderwijsEntity.datumuitspraak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumzitting">Datumzitting</span>
          </dt>
          <dd>
            {procesverbaalonderwijsEntity.datumzitting ? (
              <TextFormat value={procesverbaalonderwijsEntity.datumzitting} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geldboete">Geldboete</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.geldboete}</dd>
          <dt>
            <span id="geldboetevoorwaardelijk">Geldboetevoorwaardelijk</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.geldboetevoorwaardelijk}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.opmerkingen}</dd>
          <dt>
            <span id="proeftijd">Proeftijd</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.proeftijd}</dd>
          <dt>
            <span id="reden">Reden</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.reden}</dd>
          <dt>
            <span id="sanctiesoort">Sanctiesoort</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.sanctiesoort}</dd>
          <dt>
            <span id="uitspraak">Uitspraak</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.uitspraak}</dd>
          <dt>
            <span id="verzuimsoort">Verzuimsoort</span>
          </dt>
          <dd>{procesverbaalonderwijsEntity.verzuimsoort}</dd>
        </dl>
        <Button tag={Link} to="/procesverbaalonderwijs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/procesverbaalonderwijs/${procesverbaalonderwijsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProcesverbaalonderwijsDetail;
