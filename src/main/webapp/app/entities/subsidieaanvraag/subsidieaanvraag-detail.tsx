import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subsidieaanvraag.reducer';

export const SubsidieaanvraagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subsidieaanvraagEntity = useAppSelector(state => state.subsidieaanvraag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsidieaanvraagDetailsHeading">Subsidieaanvraag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subsidieaanvraagEntity.id}</dd>
          <dt>
            <span id="aangevraagdbedrag">Aangevraagdbedrag</span>
          </dt>
          <dd>{subsidieaanvraagEntity.aangevraagdbedrag}</dd>
          <dt>
            <span id="datumindiening">Datumindiening</span>
          </dt>
          <dd>
            {subsidieaanvraagEntity.datumindiening ? (
              <TextFormat value={subsidieaanvraagEntity.datumindiening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{subsidieaanvraagEntity.kenmerk}</dd>
          <dt>
            <span id="ontvangstbevestiging">Ontvangstbevestiging</span>
          </dt>
          <dd>
            {subsidieaanvraagEntity.ontvangstbevestiging ? (
              <TextFormat value={subsidieaanvraagEntity.ontvangstbevestiging} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="verwachtebeschikking">Verwachtebeschikking</span>
          </dt>
          <dd>
            {subsidieaanvraagEntity.verwachtebeschikking ? (
              <TextFormat value={subsidieaanvraagEntity.verwachtebeschikking} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Betreft Subsidie</dt>
          <dd>{subsidieaanvraagEntity.betreftSubsidie ? subsidieaanvraagEntity.betreftSubsidie.id : ''}</dd>
          <dt>Mondtuit Subsidiebeschikking</dt>
          <dd>{subsidieaanvraagEntity.mondtuitSubsidiebeschikking ? subsidieaanvraagEntity.mondtuitSubsidiebeschikking.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subsidieaanvraag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsidieaanvraag/${subsidieaanvraagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubsidieaanvraagDetail;
