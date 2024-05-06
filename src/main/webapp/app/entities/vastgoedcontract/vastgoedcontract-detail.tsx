import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vastgoedcontract.reducer';

export const VastgoedcontractDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vastgoedcontractEntity = useAppSelector(state => state.vastgoedcontract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vastgoedcontractDetailsHeading">Vastgoedcontract</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vastgoedcontractEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{vastgoedcontractEntity.beschrijving}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {vastgoedcontractEntity.datumeinde ? (
              <TextFormat value={vastgoedcontractEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {vastgoedcontractEntity.datumstart ? (
              <TextFormat value={vastgoedcontractEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{vastgoedcontractEntity.identificatie}</dd>
          <dt>
            <span id="maandbedrag">Maandbedrag</span>
          </dt>
          <dd>{vastgoedcontractEntity.maandbedrag}</dd>
          <dt>
            <span id="opzegtermijn">Opzegtermijn</span>
          </dt>
          <dd>{vastgoedcontractEntity.opzegtermijn}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{vastgoedcontractEntity.status}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{vastgoedcontractEntity.type}</dd>
          <dt>Heeft Rechtspersoon</dt>
          <dd>{vastgoedcontractEntity.heeftRechtspersoon ? vastgoedcontractEntity.heeftRechtspersoon.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vastgoedcontract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vastgoedcontract/${vastgoedcontractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VastgoedcontractDetail;
