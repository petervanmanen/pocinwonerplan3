import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hoofdstuk.reducer';

export const HoofdstukDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hoofdstukEntity = useAppSelector(state => state.hoofdstuk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hoofdstukDetailsHeading">Hoofdstuk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{hoofdstukEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{hoofdstukEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{hoofdstukEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{hoofdstukEntity.omschrijving}</dd>
          <dt>Binnen Periode</dt>
          <dd>
            {hoofdstukEntity.binnenPeriodes
              ? hoofdstukEntity.binnenPeriodes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {hoofdstukEntity.binnenPeriodes && i === hoofdstukEntity.binnenPeriodes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/hoofdstuk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hoofdstuk/${hoofdstukEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoofdstukDetail;
