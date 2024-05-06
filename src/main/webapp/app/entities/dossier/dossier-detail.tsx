import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dossier.reducer';

export const DossierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dossierEntity = useAppSelector(state => state.dossier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dossierDetailsHeading">Dossier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dossierEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{dossierEntity.naam}</dd>
          <dt>Hoortbij Raadsstuk</dt>
          <dd>
            {dossierEntity.hoortbijRaadsstuks
              ? dossierEntity.hoortbijRaadsstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {dossierEntity.hoortbijRaadsstuks && i === dossierEntity.hoortbijRaadsstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/dossier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dossier/${dossierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DossierDetail;
