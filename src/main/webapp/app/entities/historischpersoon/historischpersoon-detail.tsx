import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './historischpersoon.reducer';

export const HistorischpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const historischpersoonEntity = useAppSelector(state => state.historischpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="historischpersoonDetailsHeading">Historischpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{historischpersoonEntity.id}</dd>
          <dt>
            <span id="beroep">Beroep</span>
          </dt>
          <dd>{historischpersoonEntity.beroep}</dd>
          <dt>
            <span id="datumgeboorte">Datumgeboorte</span>
          </dt>
          <dd>
            {historischpersoonEntity.datumgeboorte ? (
              <TextFormat value={historischpersoonEntity.datumgeboorte} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumoverlijden">Datumoverlijden</span>
          </dt>
          <dd>
            {historischpersoonEntity.datumoverlijden ? (
              <TextFormat value={historischpersoonEntity.datumoverlijden} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{historischpersoonEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{historischpersoonEntity.omschrijving}</dd>
          <dt>
            <span id="publiektoegankelijk">Publiektoegankelijk</span>
          </dt>
          <dd>{historischpersoonEntity.publiektoegankelijk}</dd>
          <dt>
            <span id="woondeop">Woondeop</span>
          </dt>
          <dd>{historischpersoonEntity.woondeop}</dd>
        </dl>
        <Button tag={Link} to="/historischpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/historischpersoon/${historischpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HistorischpersoonDetail;
