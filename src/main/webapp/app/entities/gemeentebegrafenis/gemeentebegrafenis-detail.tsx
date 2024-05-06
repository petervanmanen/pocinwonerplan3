import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gemeentebegrafenis.reducer';

export const GemeentebegrafenisDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gemeentebegrafenisEntity = useAppSelector(state => state.gemeentebegrafenis.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gemeentebegrafenisDetailsHeading">Gemeentebegrafenis</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.id}</dd>
          <dt>
            <span id="achtergrondmelding">Achtergrondmelding</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.achtergrondmelding}</dd>
          <dt>
            <span id="begrafeniskosten">Begrafeniskosten</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.begrafeniskosten}</dd>
          <dt>
            <span id="datumafgedaan">Datumafgedaan</span>
          </dt>
          <dd>
            {gemeentebegrafenisEntity.datumafgedaan ? (
              <TextFormat value={gemeentebegrafenisEntity.datumafgedaan} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumbegrafenis">Datumbegrafenis</span>
          </dt>
          <dd>
            {gemeentebegrafenisEntity.datumbegrafenis ? (
              <TextFormat value={gemeentebegrafenisEntity.datumbegrafenis} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumgemeld">Datumgemeld</span>
          </dt>
          <dd>
            {gemeentebegrafenisEntity.datumgemeld ? (
              <TextFormat value={gemeentebegrafenisEntity.datumgemeld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumruiminggraf">Datumruiminggraf</span>
          </dt>
          <dd>
            {gemeentebegrafenisEntity.datumruiminggraf ? (
              <TextFormat value={gemeentebegrafenisEntity.datumruiminggraf} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="doodsoorzaak">Doodsoorzaak</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.doodsoorzaak}</dd>
          <dt>
            <span id="gemeentelijkekosten">Gemeentelijkekosten</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.gemeentelijkekosten}</dd>
          <dt>
            <span id="inkoopordernummer">Inkoopordernummer</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.inkoopordernummer}</dd>
          <dt>
            <span id="melder">Melder</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.melder}</dd>
          <dt>
            <span id="urengemeente">Urengemeente</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.urengemeente}</dd>
          <dt>
            <span id="verhaaldbedrag">Verhaaldbedrag</span>
          </dt>
          <dd>{gemeentebegrafenisEntity.verhaaldbedrag}</dd>
        </dl>
        <Button tag={Link} to="/gemeentebegrafenis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gemeentebegrafenis/${gemeentebegrafenisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GemeentebegrafenisDetail;
