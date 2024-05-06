import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aomstatus.reducer';

export const AomstatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aomstatusEntity = useAppSelector(state => state.aomstatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aomstatusDetailsHeading">Aomstatus</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aomstatusEntity.id}</dd>
          <dt>
            <span id="datumbeginstatus">Datumbeginstatus</span>
          </dt>
          <dd>
            {aomstatusEntity.datumbeginstatus ? (
              <TextFormat value={aomstatusEntity.datumbeginstatus} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindestatus">Datumeindestatus</span>
          </dt>
          <dd>
            {aomstatusEntity.datumeindestatus ? (
              <TextFormat value={aomstatusEntity.datumeindestatus} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{aomstatusEntity.status}</dd>
          <dt>
            <span id="statuscode">Statuscode</span>
          </dt>
          <dd>{aomstatusEntity.statuscode}</dd>
          <dt>
            <span id="statusvolgorde">Statusvolgorde</span>
          </dt>
          <dd>{aomstatusEntity.statusvolgorde}</dd>
        </dl>
        <Button tag={Link} to="/aomstatus" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aomstatus/${aomstatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AomstatusDetail;
