import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './betaalmoment.reducer';

export const BetaalmomentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const betaalmomentEntity = useAppSelector(state => state.betaalmoment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="betaalmomentDetailsHeading">Betaalmoment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{betaalmomentEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{betaalmomentEntity.bedrag}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {betaalmomentEntity.datum ? <TextFormat value={betaalmomentEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="voorschot">Voorschot</span>
          </dt>
          <dd>{betaalmomentEntity.voorschot ? 'true' : 'false'}</dd>
          <dt>Heeft Subsidiecomponent</dt>
          <dd>{betaalmomentEntity.heeftSubsidiecomponent ? betaalmomentEntity.heeftSubsidiecomponent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/betaalmoment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/betaalmoment/${betaalmomentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BetaalmomentDetail;
