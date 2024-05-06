import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './legesgrondslag.reducer';

export const LegesgrondslagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const legesgrondslagEntity = useAppSelector(state => state.legesgrondslag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="legesgrondslagDetailsHeading">Legesgrondslag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{legesgrondslagEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{legesgrondslagEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="aantalopgegeven">Aantalopgegeven</span>
          </dt>
          <dd>{legesgrondslagEntity.aantalopgegeven}</dd>
          <dt>
            <span id="aantalvastgesteld">Aantalvastgesteld</span>
          </dt>
          <dd>{legesgrondslagEntity.aantalvastgesteld}</dd>
          <dt>
            <span id="automatisch">Automatisch</span>
          </dt>
          <dd>{legesgrondslagEntity.automatisch}</dd>
          <dt>
            <span id="datumaanmaak">Datumaanmaak</span>
          </dt>
          <dd>{legesgrondslagEntity.datumaanmaak}</dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {legesgrondslagEntity.datummutatie ? (
              <TextFormat value={legesgrondslagEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{legesgrondslagEntity.eenheid}</dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{legesgrondslagEntity.gemuteerddoor}</dd>
          <dt>
            <span id="legesgrondslag">Legesgrondslag</span>
          </dt>
          <dd>{legesgrondslagEntity.legesgrondslag}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{legesgrondslagEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/legesgrondslag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/legesgrondslag/${legesgrondslagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LegesgrondslagDetail;
