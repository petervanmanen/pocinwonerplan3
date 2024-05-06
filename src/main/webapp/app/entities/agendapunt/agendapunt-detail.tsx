import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './agendapunt.reducer';

export const AgendapuntDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const agendapuntEntity = useAppSelector(state => state.agendapunt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="agendapuntDetailsHeading">Agendapunt</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{agendapuntEntity.id}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{agendapuntEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{agendapuntEntity.omschrijving}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{agendapuntEntity.titel}</dd>
          <dt>Heeft Vergadering</dt>
          <dd>{agendapuntEntity.heeftVergadering ? agendapuntEntity.heeftVergadering.id : ''}</dd>
          <dt>Behandelt Raadsstuk</dt>
          <dd>
            {agendapuntEntity.behandeltRaadsstuks
              ? agendapuntEntity.behandeltRaadsstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {agendapuntEntity.behandeltRaadsstuks && i === agendapuntEntity.behandeltRaadsstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/agendapunt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agendapunt/${agendapuntEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgendapuntDetail;
