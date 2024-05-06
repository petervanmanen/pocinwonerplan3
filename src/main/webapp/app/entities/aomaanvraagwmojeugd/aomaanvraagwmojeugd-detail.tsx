import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aomaanvraagwmojeugd.reducer';

export const AomaanvraagwmojeugdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aomaanvraagwmojeugdEntity = useAppSelector(state => state.aomaanvraagwmojeugd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aomaanvraagwmojeugdDetailsHeading">Aomaanvraagwmojeugd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.id}</dd>
          <dt>
            <span id="clientreactie">Clientreactie</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.clientreactie}</dd>
          <dt>
            <span id="datumbeschikking">Datumbeschikking</span>
          </dt>
          <dd>
            {aomaanvraagwmojeugdEntity.datumbeschikking ? (
              <TextFormat value={aomaanvraagwmojeugdEntity.datumbeschikking} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeersteafspraak">Datumeersteafspraak</span>
          </dt>
          <dd>
            {aomaanvraagwmojeugdEntity.datumeersteafspraak ? (
              <TextFormat value={aomaanvraagwmojeugdEntity.datumeersteafspraak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {aomaanvraagwmojeugdEntity.datumeinde ? (
              <TextFormat value={aomaanvraagwmojeugdEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumplanvastgesteld">Datumplanvastgesteld</span>
          </dt>
          <dd>
            {aomaanvraagwmojeugdEntity.datumplanvastgesteld ? (
              <TextFormat value={aomaanvraagwmojeugdEntity.datumplanvastgesteld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstartaanvraag">Datumstartaanvraag</span>
          </dt>
          <dd>
            {aomaanvraagwmojeugdEntity.datumstartaanvraag ? (
              <TextFormat value={aomaanvraagwmojeugdEntity.datumstartaanvraag} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deskundigheid">Deskundigheid</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.deskundigheid}</dd>
          <dt>
            <span id="doorloopmethodiek">Doorloopmethodiek</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.doorloopmethodiek}</dd>
          <dt>
            <span id="maximaledoorlooptijd">Maximaledoorlooptijd</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.maximaledoorlooptijd}</dd>
          <dt>
            <span id="redenafsluiting">Redenafsluiting</span>
          </dt>
          <dd>{aomaanvraagwmojeugdEntity.redenafsluiting}</dd>
        </dl>
        <Button tag={Link} to="/aomaanvraagwmojeugd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aomaanvraagwmojeugd/${aomaanvraagwmojeugdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AomaanvraagwmojeugdDetail;
