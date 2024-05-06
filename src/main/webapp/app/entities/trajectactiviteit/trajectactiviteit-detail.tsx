import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trajectactiviteit.reducer';

export const TrajectactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trajectactiviteitEntity = useAppSelector(state => state.trajectactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trajectactiviteitDetailsHeading">Trajectactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trajectactiviteitEntity.id}</dd>
          <dt>
            <span id="contract">Contract</span>
          </dt>
          <dd>{trajectactiviteitEntity.contract}</dd>
          <dt>
            <span id="crediteur">Crediteur</span>
          </dt>
          <dd>{trajectactiviteitEntity.crediteur}</dd>
          <dt>
            <span id="datumbegin">Datumbegin</span>
          </dt>
          <dd>
            {trajectactiviteitEntity.datumbegin ? (
              <TextFormat value={trajectactiviteitEntity.datumbegin} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {trajectactiviteitEntity.datumeinde ? (
              <TextFormat value={trajectactiviteitEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstatus">Datumstatus</span>
          </dt>
          <dd>
            {trajectactiviteitEntity.datumstatus ? (
              <TextFormat value={trajectactiviteitEntity.datumstatus} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="kinderopvang">Kinderopvang</span>
          </dt>
          <dd>{trajectactiviteitEntity.kinderopvang}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{trajectactiviteitEntity.status}</dd>
          <dt>
            <span id="succesvol">Succesvol</span>
          </dt>
          <dd>{trajectactiviteitEntity.succesvol}</dd>
        </dl>
        <Button tag={Link} to="/trajectactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trajectactiviteit/${trajectactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrajectactiviteitDetail;
