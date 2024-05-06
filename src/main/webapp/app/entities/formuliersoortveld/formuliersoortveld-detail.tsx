import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './formuliersoortveld.reducer';

export const FormuliersoortveldDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formuliersoortveldEntity = useAppSelector(state => state.formuliersoortveld.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formuliersoortveldDetailsHeading">Formuliersoortveld</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{formuliersoortveldEntity.id}</dd>
          <dt>
            <span id="helptekst">Helptekst</span>
          </dt>
          <dd>{formuliersoortveldEntity.helptekst}</dd>
          <dt>
            <span id="isverplicht">Isverplicht</span>
          </dt>
          <dd>{formuliersoortveldEntity.isverplicht ? 'true' : 'false'}</dd>
          <dt>
            <span id="label">Label</span>
          </dt>
          <dd>{formuliersoortveldEntity.label}</dd>
          <dt>
            <span id="maxlengte">Maxlengte</span>
          </dt>
          <dd>{formuliersoortveldEntity.maxlengte}</dd>
          <dt>
            <span id="veldnaam">Veldnaam</span>
          </dt>
          <dd>{formuliersoortveldEntity.veldnaam}</dd>
          <dt>
            <span id="veldtype">Veldtype</span>
          </dt>
          <dd>{formuliersoortveldEntity.veldtype}</dd>
          <dt>Heeftvelden Formuliersoort</dt>
          <dd>{formuliersoortveldEntity.heeftveldenFormuliersoort ? formuliersoortveldEntity.heeftveldenFormuliersoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/formuliersoortveld" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formuliersoortveld/${formuliersoortveldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormuliersoortveldDetail;
