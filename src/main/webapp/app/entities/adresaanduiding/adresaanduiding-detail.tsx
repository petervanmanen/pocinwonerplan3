import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adresaanduiding.reducer';

export const AdresaanduidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adresaanduidingEntity = useAppSelector(state => state.adresaanduiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adresaanduidingDetailsHeading">Adresaanduiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{adresaanduidingEntity.id}</dd>
          <dt>
            <span id="adresaanduiding">Adresaanduiding</span>
          </dt>
          <dd>{adresaanduidingEntity.adresaanduiding}</dd>
          <dt>Verwijstnaar Nummeraanduiding</dt>
          <dd>{adresaanduidingEntity.verwijstnaarNummeraanduiding ? adresaanduidingEntity.verwijstnaarNummeraanduiding.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adresaanduiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adresaanduiding/${adresaanduidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdresaanduidingDetail;
