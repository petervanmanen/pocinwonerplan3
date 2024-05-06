import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './valutasoort.reducer';

export const ValutasoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const valutasoortEntity = useAppSelector(state => state.valutasoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="valutasoortDetailsHeading">Valutasoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{valutasoortEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidvalutasoort">Datumbegingeldigheidvalutasoort</span>
          </dt>
          <dd>
            {valutasoortEntity.datumbegingeldigheidvalutasoort ? (
              <TextFormat value={valutasoortEntity.datumbegingeldigheidvalutasoort} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidvalutasoort">Datumeindegeldigheidvalutasoort</span>
          </dt>
          <dd>
            {valutasoortEntity.datumeindegeldigheidvalutasoort ? (
              <TextFormat value={valutasoortEntity.datumeindegeldigheidvalutasoort} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naamvaluta">Naamvaluta</span>
          </dt>
          <dd>{valutasoortEntity.naamvaluta}</dd>
          <dt>
            <span id="valutacode">Valutacode</span>
          </dt>
          <dd>{valutasoortEntity.valutacode}</dd>
        </dl>
        <Button tag={Link} to="/valutasoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/valutasoort/${valutasoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ValutasoortDetail;
