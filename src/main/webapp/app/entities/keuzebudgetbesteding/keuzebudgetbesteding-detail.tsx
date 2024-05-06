import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './keuzebudgetbesteding.reducer';

export const KeuzebudgetbestedingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const keuzebudgetbestedingEntity = useAppSelector(state => state.keuzebudgetbesteding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="keuzebudgetbestedingDetailsHeading">Keuzebudgetbesteding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{keuzebudgetbestedingEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{keuzebudgetbestedingEntity.bedrag}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {keuzebudgetbestedingEntity.datum ? (
              <TextFormat value={keuzebudgetbestedingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/keuzebudgetbesteding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/keuzebudgetbesteding/${keuzebudgetbestedingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KeuzebudgetbestedingDetail;
