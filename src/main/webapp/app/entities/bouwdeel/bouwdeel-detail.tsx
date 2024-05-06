import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwdeel.reducer';

export const BouwdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwdeelEntity = useAppSelector(state => state.bouwdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwdeelDetailsHeading">Bouwdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwdeelEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{bouwdeelEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{bouwdeelEntity.omschrijving}</dd>
          <dt>Bestaatuit Vastgoedobject</dt>
          <dd>{bouwdeelEntity.bestaatuitVastgoedobject ? bouwdeelEntity.bestaatuitVastgoedobject.id : ''}</dd>
          <dt>Betreft Werkbon</dt>
          <dd>
            {bouwdeelEntity.betreftWerkbons
              ? bouwdeelEntity.betreftWerkbons.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bouwdeelEntity.betreftWerkbons && i === bouwdeelEntity.betreftWerkbons.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bouwdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwdeel/${bouwdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwdeelDetail;
