import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vastgoedcontractregel.reducer';

export const VastgoedcontractregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vastgoedcontractregelEntity = useAppSelector(state => state.vastgoedcontractregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vastgoedcontractregelDetailsHeading">Vastgoedcontractregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.bedrag}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {vastgoedcontractregelEntity.datumeinde ? (
              <TextFormat value={vastgoedcontractregelEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {vastgoedcontractregelEntity.datumstart ? (
              <TextFormat value={vastgoedcontractregelEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.frequentie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.identificatie}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.omschrijving}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.status}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{vastgoedcontractregelEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/vastgoedcontractregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vastgoedcontractregel/${vastgoedcontractregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VastgoedcontractregelDetail;
