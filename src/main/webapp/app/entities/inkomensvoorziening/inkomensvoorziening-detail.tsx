import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inkomensvoorziening.reducer';

export const InkomensvoorzieningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inkomensvoorzieningEntity = useAppSelector(state => state.inkomensvoorziening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inkomensvoorzieningDetailsHeading">Inkomensvoorziening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.bedrag}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.datumstart}</dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.datumtoekenning}</dd>
          <dt>
            <span id="eenmalig">Eenmalig</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.eenmalig ? 'true' : 'false'}</dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{inkomensvoorzieningEntity.groep}</dd>
          <dt>Empty Clientbegeleider</dt>
          <dd>{inkomensvoorzieningEntity.emptyClientbegeleider ? inkomensvoorzieningEntity.emptyClientbegeleider.id : ''}</dd>
          <dt>Issoortvoorziening Inkomensvoorzieningsoort</dt>
          <dd>
            {inkomensvoorzieningEntity.issoortvoorzieningInkomensvoorzieningsoort
              ? inkomensvoorzieningEntity.issoortvoorzieningInkomensvoorzieningsoort.id
              : ''}
          </dd>
          <dt>Voorzieningbijstandspartij Client</dt>
          <dd>
            {inkomensvoorzieningEntity.voorzieningbijstandspartijClients
              ? inkomensvoorzieningEntity.voorzieningbijstandspartijClients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {inkomensvoorzieningEntity.voorzieningbijstandspartijClients &&
                    i === inkomensvoorzieningEntity.voorzieningbijstandspartijClients.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/inkomensvoorziening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inkomensvoorziening/${inkomensvoorzieningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InkomensvoorzieningDetail;
