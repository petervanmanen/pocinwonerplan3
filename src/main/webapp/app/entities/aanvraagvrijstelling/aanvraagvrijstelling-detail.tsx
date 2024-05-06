import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanvraagvrijstelling.reducer';

export const AanvraagvrijstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanvraagvrijstellingEntity = useAppSelector(state => state.aanvraagvrijstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanvraagvrijstellingDetailsHeading">Aanvraagvrijstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanvraagvrijstellingEntity.id}</dd>
          <dt>
            <span id="buitenlandseschoollocatie">Buitenlandseschoollocatie</span>
          </dt>
          <dd>{aanvraagvrijstellingEntity.buitenlandseschoollocatie}</dd>
          <dt>
            <span id="datumaanvraag">Datumaanvraag</span>
          </dt>
          <dd>
            {aanvraagvrijstellingEntity.datumaanvraag ? (
              <TextFormat value={aanvraagvrijstellingEntity.datumaanvraag} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/aanvraagvrijstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanvraagvrijstelling/${aanvraagvrijstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanvraagvrijstellingDetail;
