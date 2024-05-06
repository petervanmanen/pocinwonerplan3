import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './digitaalbestand.reducer';

export const DigitaalbestandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const digitaalbestandEntity = useAppSelector(state => state.digitaalbestand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="digitaalbestandDetailsHeading">Digitaalbestand</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{digitaalbestandEntity.id}</dd>
          <dt>
            <span id="blob">Blob</span>
          </dt>
          <dd>{digitaalbestandEntity.blob}</dd>
          <dt>
            <span id="mimetype">Mimetype</span>
          </dt>
          <dd>{digitaalbestandEntity.mimetype}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{digitaalbestandEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{digitaalbestandEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/digitaalbestand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/digitaalbestand/${digitaalbestandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DigitaalbestandDetail;
