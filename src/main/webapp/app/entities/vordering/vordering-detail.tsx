import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vordering.reducer';

export const VorderingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vorderingEntity = useAppSelector(state => state.vordering.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vorderingDetailsHeading">Vordering</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vorderingEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{vorderingEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="bedragbtw">Bedragbtw</span>
          </dt>
          <dd>{vorderingEntity.bedragbtw}</dd>
          <dt>
            <span id="datumaanmaak">Datumaanmaak</span>
          </dt>
          <dd>
            {vorderingEntity.datumaanmaak ? (
              <TextFormat value={vorderingEntity.datumaanmaak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {vorderingEntity.datummutatie ? (
              <TextFormat value={vorderingEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geaccordeerd">Geaccordeerd</span>
          </dt>
          <dd>{vorderingEntity.geaccordeerd}</dd>
          <dt>
            <span id="geaccordeerddoor">Geaccordeerddoor</span>
          </dt>
          <dd>{vorderingEntity.geaccordeerddoor}</dd>
          <dt>
            <span id="geaccordeerdop">Geaccordeerdop</span>
          </dt>
          <dd>
            {vorderingEntity.geaccordeerdop ? (
              <TextFormat value={vorderingEntity.geaccordeerdop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geexporteerd">Geexporteerd</span>
          </dt>
          <dd>{vorderingEntity.geexporteerd}</dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{vorderingEntity.gemuteerddoor}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{vorderingEntity.omschrijving}</dd>
          <dt>
            <span id="totaalbedrag">Totaalbedrag</span>
          </dt>
          <dd>{vorderingEntity.totaalbedrag}</dd>
          <dt>
            <span id="totaalbedraginclusief">Totaalbedraginclusief</span>
          </dt>
          <dd>{vorderingEntity.totaalbedraginclusief}</dd>
          <dt>
            <span id="vorderingnummer">Vorderingnummer</span>
          </dt>
          <dd>{vorderingEntity.vorderingnummer}</dd>
        </dl>
        <Button tag={Link} to="/vordering" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vordering/${vorderingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VorderingDetail;
