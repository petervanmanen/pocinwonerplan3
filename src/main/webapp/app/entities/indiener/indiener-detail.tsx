import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './indiener.reducer';

export const IndienerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const indienerEntity = useAppSelector(state => state.indiener.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="indienerDetailsHeading">Indiener</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{indienerEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{indienerEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{indienerEntity.omschrijving}</dd>
          <dt>Is Collegelid</dt>
          <dd>{indienerEntity.isCollegelid ? indienerEntity.isCollegelid.id : ''}</dd>
          <dt>Is Raadslid</dt>
          <dd>{indienerEntity.isRaadslid ? indienerEntity.isRaadslid.id : ''}</dd>
          <dt>Is Rechtspersoon</dt>
          <dd>{indienerEntity.isRechtspersoon ? indienerEntity.isRechtspersoon.id : ''}</dd>
          <dt>Heeft Raadsstuk</dt>
          <dd>
            {indienerEntity.heeftRaadsstuks
              ? indienerEntity.heeftRaadsstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {indienerEntity.heeftRaadsstuks && i === indienerEntity.heeftRaadsstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/indiener" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/indiener/${indienerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndienerDetail;
