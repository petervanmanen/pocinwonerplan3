import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inzet.reducer';

export const InzetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inzetEntity = useAppSelector(state => state.inzet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inzetDetailsHeading">Inzet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inzetEntity.id}</dd>
          <dt>
            <span id="datumbegin">Datumbegin</span>
          </dt>
          <dd>
            {inzetEntity.datumbegin ? <TextFormat value={inzetEntity.datumbegin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {inzetEntity.datumeinde ? <TextFormat value={inzetEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="percentage">Percentage</span>
          </dt>
          <dd>{inzetEntity.percentage}</dd>
          <dt>
            <span id="uren">Uren</span>
          </dt>
          <dd>{inzetEntity.uren}</dd>
        </dl>
        <Button tag={Link} to="/inzet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inzet/${inzetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InzetDetail;
