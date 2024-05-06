import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './naheffing.reducer';

export const NaheffingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const naheffingEntity = useAppSelector(state => state.naheffing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="naheffingDetailsHeading">Naheffing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{naheffingEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{naheffingEntity.bedrag}</dd>
          <dt>
            <span id="bezwaarafgehandeld">Bezwaarafgehandeld</span>
          </dt>
          <dd>
            {naheffingEntity.bezwaarafgehandeld ? (
              <TextFormat value={naheffingEntity.bezwaarafgehandeld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bezwaaringetrokken">Bezwaaringetrokken</span>
          </dt>
          <dd>
            {naheffingEntity.bezwaaringetrokken ? (
              <TextFormat value={naheffingEntity.bezwaaringetrokken} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bezwaartoegewezen">Bezwaartoegewezen</span>
          </dt>
          <dd>
            {naheffingEntity.bezwaartoegewezen ? (
              <TextFormat value={naheffingEntity.bezwaartoegewezen} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bonnummer">Bonnummer</span>
          </dt>
          <dd>{naheffingEntity.bonnummer}</dd>
          <dt>
            <span id="datumbetaling">Datumbetaling</span>
          </dt>
          <dd>
            {naheffingEntity.datumbetaling ? (
              <TextFormat value={naheffingEntity.datumbetaling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumbezwaar">Datumbezwaar</span>
          </dt>
          <dd>
            {naheffingEntity.datumbezwaar ? (
              <TextFormat value={naheffingEntity.datumbezwaar} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumgeseponeerd">Datumgeseponeerd</span>
          </dt>
          <dd>
            {naheffingEntity.datumgeseponeerd ? (
              <TextFormat value={naheffingEntity.datumgeseponeerd} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumindiening">Datumindiening</span>
          </dt>
          <dd>
            {naheffingEntity.datumindiening ? (
              <TextFormat value={naheffingEntity.datumindiening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dienstcd">Dienstcd</span>
          </dt>
          <dd>{naheffingEntity.dienstcd}</dd>
          <dt>
            <span id="fiscaal">Fiscaal</span>
          </dt>
          <dd>{naheffingEntity.fiscaal ? 'true' : 'false'}</dd>
          <dt>
            <span id="organisatie">Organisatie</span>
          </dt>
          <dd>{naheffingEntity.organisatie}</dd>
          <dt>
            <span id="overtreding">Overtreding</span>
          </dt>
          <dd>{naheffingEntity.overtreding}</dd>
          <dt>
            <span id="parkeertarief">Parkeertarief</span>
          </dt>
          <dd>{naheffingEntity.parkeertarief}</dd>
          <dt>
            <span id="redenseponeren">Redenseponeren</span>
          </dt>
          <dd>{naheffingEntity.redenseponeren}</dd>
          <dt>
            <span id="vorderingnummer">Vorderingnummer</span>
          </dt>
          <dd>{naheffingEntity.vorderingnummer}</dd>
        </dl>
        <Button tag={Link} to="/naheffing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/naheffing/${naheffingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NaheffingDetail;
