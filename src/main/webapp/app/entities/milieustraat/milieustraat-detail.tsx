import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './milieustraat.reducer';

export const MilieustraatDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const milieustraatEntity = useAppSelector(state => state.milieustraat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="milieustraatDetailsHeading">Milieustraat</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{milieustraatEntity.id}</dd>
          <dt>
            <span id="adresaanduiding">Adresaanduiding</span>
          </dt>
          <dd>{milieustraatEntity.adresaanduiding}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{milieustraatEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{milieustraatEntity.omschrijving}</dd>
          <dt>Inzamelpuntvan Fractie</dt>
          <dd>
            {milieustraatEntity.inzamelpuntvanFracties
              ? milieustraatEntity.inzamelpuntvanFracties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {milieustraatEntity.inzamelpuntvanFracties && i === milieustraatEntity.inzamelpuntvanFracties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Geldigvoor Pas</dt>
          <dd>
            {milieustraatEntity.geldigvoorPas
              ? milieustraatEntity.geldigvoorPas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {milieustraatEntity.geldigvoorPas && i === milieustraatEntity.geldigvoorPas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/milieustraat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/milieustraat/${milieustraatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MilieustraatDetail;
