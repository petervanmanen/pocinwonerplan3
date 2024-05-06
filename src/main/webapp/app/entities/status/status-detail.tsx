import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './status.reducer';

export const StatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const statusEntity = useAppSelector(state => state.status.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="statusDetailsHeading">Status</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{statusEntity.id}</dd>
          <dt>
            <span id="datumstatusgezet">Datumstatusgezet</span>
          </dt>
          <dd>{statusEntity.datumstatusgezet}</dd>
          <dt>
            <span id="indicatieiaatstgezettestatus">Indicatieiaatstgezettestatus</span>
          </dt>
          <dd>{statusEntity.indicatieiaatstgezettestatus}</dd>
          <dt>
            <span id="statustoelichting">Statustoelichting</span>
          </dt>
          <dd>{statusEntity.statustoelichting}</dd>
          <dt>Isvan Statustype</dt>
          <dd>{statusEntity.isvanStatustype ? statusEntity.isvanStatustype.id : ''}</dd>
          <dt>Heeft Zaak</dt>
          <dd>{statusEntity.heeftZaak ? statusEntity.heeftZaak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/status/${statusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StatusDetail;
