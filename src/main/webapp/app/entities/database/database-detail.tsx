import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './database.reducer';

export const DatabaseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const databaseEntity = useAppSelector(state => state.database.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="databaseDetailsHeading">Database</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{databaseEntity.id}</dd>
          <dt>
            <span id="architectuur">Architectuur</span>
          </dt>
          <dd>{databaseEntity.architectuur}</dd>
          <dt>
            <span id="database">Database</span>
          </dt>
          <dd>{databaseEntity.database}</dd>
          <dt>
            <span id="databaseversie">Databaseversie</span>
          </dt>
          <dd>{databaseEntity.databaseversie}</dd>
          <dt>
            <span id="dbms">Dbms</span>
          </dt>
          <dd>{databaseEntity.dbms}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{databaseEntity.omschrijving}</dd>
          <dt>
            <span id="otap">Otap</span>
          </dt>
          <dd>{databaseEntity.otap}</dd>
          <dt>
            <span id="vlan">Vlan</span>
          </dt>
          <dd>{databaseEntity.vlan}</dd>
          <dt>Servervandatabase Server</dt>
          <dd>{databaseEntity.servervandatabaseServer ? databaseEntity.servervandatabaseServer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/database" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/database/${databaseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DatabaseDetail;
