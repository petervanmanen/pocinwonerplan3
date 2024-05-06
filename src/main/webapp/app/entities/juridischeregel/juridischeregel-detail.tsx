import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './juridischeregel.reducer';

export const JuridischeregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const juridischeregelEntity = useAppSelector(state => state.juridischeregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="juridischeregelDetailsHeading">Juridischeregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{juridischeregelEntity.id}</dd>
          <dt>
            <span id="datumbekend">Datumbekend</span>
          </dt>
          <dd>
            {juridischeregelEntity.datumbekend ? (
              <TextFormat value={juridischeregelEntity.datumbekend} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {juridischeregelEntity.datumeindegeldigheid ? (
              <TextFormat value={juridischeregelEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datuminwerking">Datuminwerking</span>
          </dt>
          <dd>
            {juridischeregelEntity.datuminwerking ? (
              <TextFormat value={juridischeregelEntity.datuminwerking} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {juridischeregelEntity.datumstart ? (
              <TextFormat value={juridischeregelEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{juridischeregelEntity.omschrijving}</dd>
          <dt>
            <span id="regeltekst">Regeltekst</span>
          </dt>
          <dd>{juridischeregelEntity.regeltekst}</dd>
          <dt>
            <span id="thema">Thema</span>
          </dt>
          <dd>{juridischeregelEntity.thema}</dd>
        </dl>
        <Button tag={Link} to="/juridischeregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/juridischeregel/${juridischeregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default JuridischeregelDetail;
