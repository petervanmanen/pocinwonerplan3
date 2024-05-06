import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zekerheidsrecht.reducer';

export const ZekerheidsrechtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zekerheidsrechtEntity = useAppSelector(state => state.zekerheidsrecht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zekerheidsrechtDetailsHeading">Zekerheidsrecht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zekerheidsrechtEntity.id}</dd>
          <dt>
            <span id="aandeelinbetrokkenrecht">Aandeelinbetrokkenrecht</span>
          </dt>
          <dd>{zekerheidsrechtEntity.aandeelinbetrokkenrecht}</dd>
          <dt>
            <span id="datumeinderecht">Datumeinderecht</span>
          </dt>
          <dd>
            {zekerheidsrechtEntity.datumeinderecht ? (
              <TextFormat value={zekerheidsrechtEntity.datumeinderecht} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangrecht">Datumingangrecht</span>
          </dt>
          <dd>
            {zekerheidsrechtEntity.datumingangrecht ? (
              <TextFormat value={zekerheidsrechtEntity.datumingangrecht} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="identificatiezekerheidsrecht">Identificatiezekerheidsrecht</span>
          </dt>
          <dd>{zekerheidsrechtEntity.identificatiezekerheidsrecht}</dd>
          <dt>
            <span id="omschrijvingbetrokkenrecht">Omschrijvingbetrokkenrecht</span>
          </dt>
          <dd>{zekerheidsrechtEntity.omschrijvingbetrokkenrecht}</dd>
          <dt>
            <span id="typezekerheidsrecht">Typezekerheidsrecht</span>
          </dt>
          <dd>{zekerheidsrechtEntity.typezekerheidsrecht}</dd>
          <dt>Bezwaart Tenaamstelling</dt>
          <dd>{zekerheidsrechtEntity.bezwaartTenaamstelling ? zekerheidsrechtEntity.bezwaartTenaamstelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/zekerheidsrecht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zekerheidsrecht/${zekerheidsrechtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZekerheidsrechtDetail;
