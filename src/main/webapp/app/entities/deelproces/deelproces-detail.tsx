import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deelproces.reducer';

export const DeelprocesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const deelprocesEntity = useAppSelector(state => state.deelproces.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deelprocesDetailsHeading">Deelproces</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{deelprocesEntity.id}</dd>
          <dt>
            <span id="datumafgehandeld">Datumafgehandeld</span>
          </dt>
          <dd>
            {deelprocesEntity.datumafgehandeld ? (
              <TextFormat value={deelprocesEntity.datumafgehandeld} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumgepland">Datumgepland</span>
          </dt>
          <dd>
            {deelprocesEntity.datumgepland ? (
              <TextFormat value={deelprocesEntity.datumgepland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Isvan Deelprocestype</dt>
          <dd>{deelprocesEntity.isvanDeelprocestype ? deelprocesEntity.isvanDeelprocestype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deelproces" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deelproces/${deelprocesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeelprocesDetail;
