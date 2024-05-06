import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './haltverwijzing.reducer';

export const HaltverwijzingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const haltverwijzingEntity = useAppSelector(state => state.haltverwijzing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="haltverwijzingDetailsHeading">Haltverwijzing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{haltverwijzingEntity.id}</dd>
          <dt>
            <span id="afdoening">Afdoening</span>
          </dt>
          <dd>{haltverwijzingEntity.afdoening}</dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {haltverwijzingEntity.datummutatie ? (
              <TextFormat value={haltverwijzingEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumretour">Datumretour</span>
          </dt>
          <dd>
            {haltverwijzingEntity.datumretour ? (
              <TextFormat value={haltverwijzingEntity.datumretour} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="memo">Memo</span>
          </dt>
          <dd>{haltverwijzingEntity.memo}</dd>
        </dl>
        <Button tag={Link} to="/haltverwijzing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/haltverwijzing/${haltverwijzingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HaltverwijzingDetail;
