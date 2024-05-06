import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './batchregel.reducer';

export const BatchregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const batchregelEntity = useAppSelector(state => state.batchregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="batchregelDetailsHeading">Batchregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{batchregelEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{batchregelEntity.bedrag}</dd>
          <dt>
            <span id="datumbetaling">Datumbetaling</span>
          </dt>
          <dd>
            {batchregelEntity.datumbetaling ? (
              <TextFormat value={batchregelEntity.datumbetaling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{batchregelEntity.omschrijving}</dd>
          <dt>
            <span id="rekeningnaar">Rekeningnaar</span>
          </dt>
          <dd>{batchregelEntity.rekeningnaar}</dd>
          <dt>
            <span id="rekeningvan">Rekeningvan</span>
          </dt>
          <dd>{batchregelEntity.rekeningvan}</dd>
          <dt>Leidttot Mutatie</dt>
          <dd>{batchregelEntity.leidttotMutatie ? batchregelEntity.leidttotMutatie.id : ''}</dd>
          <dt>Heeft Batch</dt>
          <dd>{batchregelEntity.heeftBatch ? batchregelEntity.heeftBatch.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/batchregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/batchregel/${batchregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BatchregelDetail;
