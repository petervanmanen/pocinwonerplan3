import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwdeelelement.reducer';

export const BouwdeelelementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwdeelelementEntity = useAppSelector(state => state.bouwdeelelement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwdeelelementDetailsHeading">Bouwdeelelement</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwdeelelementEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{bouwdeelelementEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{bouwdeelelementEntity.omschrijving}</dd>
          <dt>Bestaatuit Bouwdeel</dt>
          <dd>{bouwdeelelementEntity.bestaatuitBouwdeel ? bouwdeelelementEntity.bestaatuitBouwdeel.id : ''}</dd>
          <dt>Betreft Werkbon</dt>
          <dd>
            {bouwdeelelementEntity.betreftWerkbons
              ? bouwdeelelementEntity.betreftWerkbons.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bouwdeelelementEntity.betreftWerkbons && i === bouwdeelelementEntity.betreftWerkbons.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bouwdeelelement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwdeelelement/${bouwdeelelementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwdeelelementDetail;
