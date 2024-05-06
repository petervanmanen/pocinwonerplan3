import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wozwaarde.reducer';

export const WozwaardeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wozwaardeEntity = useAppSelector(state => state.wozwaarde.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wozwaardeDetailsHeading">Wozwaarde</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wozwaardeEntity.id}</dd>
          <dt>
            <span id="datumpeilingtoestand">Datumpeilingtoestand</span>
          </dt>
          <dd>
            {wozwaardeEntity.datumpeilingtoestand ? (
              <TextFormat value={wozwaardeEntity.datumpeilingtoestand} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumwaardepeiling">Datumwaardepeiling</span>
          </dt>
          <dd>
            {wozwaardeEntity.datumwaardepeiling ? (
              <TextFormat value={wozwaardeEntity.datumwaardepeiling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="statusbeschikking">Statusbeschikking</span>
          </dt>
          <dd>{wozwaardeEntity.statusbeschikking}</dd>
          <dt>
            <span id="vastgesteldewaarde">Vastgesteldewaarde</span>
          </dt>
          <dd>{wozwaardeEntity.vastgesteldewaarde}</dd>
        </dl>
        <Button tag={Link} to="/wozwaarde" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wozwaarde/${wozwaardeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WozwaardeDetail;
