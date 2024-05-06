import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meldingeigenbijdrage.reducer';

export const MeldingeigenbijdrageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const meldingeigenbijdrageEntity = useAppSelector(state => state.meldingeigenbijdrage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="meldingeigenbijdrageDetailsHeading">Meldingeigenbijdrage</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{meldingeigenbijdrageEntity.id}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {meldingeigenbijdrageEntity.datumstart ? (
              <TextFormat value={meldingeigenbijdrageEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstop">Datumstop</span>
          </dt>
          <dd>
            {meldingeigenbijdrageEntity.datumstop ? (
              <TextFormat value={meldingeigenbijdrageEntity.datumstop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/meldingeigenbijdrage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meldingeigenbijdrage/${meldingeigenbijdrageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MeldingeigenbijdrageDetail;
