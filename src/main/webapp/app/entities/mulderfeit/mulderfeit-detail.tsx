import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mulderfeit.reducer';

export const MulderfeitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mulderfeitEntity = useAppSelector(state => state.mulderfeit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mulderfeitDetailsHeading">Mulderfeit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{mulderfeitEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{mulderfeitEntity.bedrag}</dd>
          <dt>
            <span id="bezwaarafgehandeld">Bezwaarafgehandeld</span>
          </dt>
          <dd>
            {mulderfeitEntity.bezwaarafgehandeld ? (
              <TextFormat value={mulderfeitEntity.bezwaarafgehandeld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bezwaaringetrokken">Bezwaaringetrokken</span>
          </dt>
          <dd>
            {mulderfeitEntity.bezwaaringetrokken ? (
              <TextFormat value={mulderfeitEntity.bezwaaringetrokken} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bezwaartoegewezen">Bezwaartoegewezen</span>
          </dt>
          <dd>
            {mulderfeitEntity.bezwaartoegewezen ? (
              <TextFormat value={mulderfeitEntity.bezwaartoegewezen} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bonnummer">Bonnummer</span>
          </dt>
          <dd>{mulderfeitEntity.bonnummer}</dd>
          <dt>
            <span id="datumbetaling">Datumbetaling</span>
          </dt>
          <dd>
            {mulderfeitEntity.datumbetaling ? (
              <TextFormat value={mulderfeitEntity.datumbetaling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumbezwaar">Datumbezwaar</span>
          </dt>
          <dd>
            {mulderfeitEntity.datumbezwaar ? (
              <TextFormat value={mulderfeitEntity.datumbezwaar} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumgeseponeerd">Datumgeseponeerd</span>
          </dt>
          <dd>
            {mulderfeitEntity.datumgeseponeerd ? (
              <TextFormat value={mulderfeitEntity.datumgeseponeerd} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumindiening">Datumindiening</span>
          </dt>
          <dd>
            {mulderfeitEntity.datumindiening ? (
              <TextFormat value={mulderfeitEntity.datumindiening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dienstcd">Dienstcd</span>
          </dt>
          <dd>{mulderfeitEntity.dienstcd}</dd>
          <dt>
            <span id="organisatie">Organisatie</span>
          </dt>
          <dd>{mulderfeitEntity.organisatie}</dd>
          <dt>
            <span id="overtreding">Overtreding</span>
          </dt>
          <dd>{mulderfeitEntity.overtreding}</dd>
          <dt>
            <span id="parkeertarief">Parkeertarief</span>
          </dt>
          <dd>{mulderfeitEntity.parkeertarief}</dd>
          <dt>
            <span id="redenseponeren">Redenseponeren</span>
          </dt>
          <dd>{mulderfeitEntity.redenseponeren}</dd>
          <dt>
            <span id="vorderingnummer">Vorderingnummer</span>
          </dt>
          <dd>{mulderfeitEntity.vorderingnummer}</dd>
        </dl>
        <Button tag={Link} to="/mulderfeit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mulderfeit/${mulderfeitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MulderfeitDetail;
