import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gebied.reducer';

export const GebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gebiedEntity = useAppSelector(state => state.gebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gebiedDetailsHeading">Gebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gebiedEntity.id}</dd>
          <dt>
            <span id="gebied">Gebied</span>
          </dt>
          <dd>{gebiedEntity.gebied}</dd>
          <dt>Komtovereen Buurt</dt>
          <dd>{gebiedEntity.komtovereenBuurt ? gebiedEntity.komtovereenBuurt.id : ''}</dd>
          <dt>Ligtin Nummeraanduiding</dt>
          <dd>
            {gebiedEntity.ligtinNummeraanduidings
              ? gebiedEntity.ligtinNummeraanduidings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {gebiedEntity.ligtinNummeraanduidings && i === gebiedEntity.ligtinNummeraanduidings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/gebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gebied/${gebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GebiedDetail;
