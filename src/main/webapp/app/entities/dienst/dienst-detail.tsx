import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dienst.reducer';

export const DienstDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dienstEntity = useAppSelector(state => state.dienst.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dienstDetailsHeading">Dienst</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dienstEntity.id}</dd>
          <dt>Start Zaaktype</dt>
          <dd>{dienstEntity.startZaaktype ? dienstEntity.startZaaktype.id : ''}</dd>
          <dt>Heeft Onderwerp</dt>
          <dd>{dienstEntity.heeftOnderwerp ? dienstEntity.heeftOnderwerp.id : ''}</dd>
          <dt>Betreft Product</dt>
          <dd>{dienstEntity.betreftProduct ? dienstEntity.betreftProduct.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dienst" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dienst/${dienstEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DienstDetail;
