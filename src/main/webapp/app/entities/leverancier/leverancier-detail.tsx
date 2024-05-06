import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leverancier.reducer';

export const LeverancierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leverancierEntity = useAppSelector(state => state.leverancier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leverancierDetailsHeading">Leverancier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leverancierEntity.id}</dd>
          <dt>
            <span id="agbcode">Agbcode</span>
          </dt>
          <dd>{leverancierEntity.agbcode}</dd>
          <dt>
            <span id="leverancierscode">Leverancierscode</span>
          </dt>
          <dd>{leverancierEntity.leverancierscode}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{leverancierEntity.naam}</dd>
          <dt>
            <span id="soortleverancier">Soortleverancier</span>
          </dt>
          <dd>{leverancierEntity.soortleverancier}</dd>
          <dt>
            <span id="soortleveranciercode">Soortleveranciercode</span>
          </dt>
          <dd>{leverancierEntity.soortleveranciercode}</dd>
          <dt>Gekwalificeerd Categorie</dt>
          <dd>
            {leverancierEntity.gekwalificeerdCategories
              ? leverancierEntity.gekwalificeerdCategories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {leverancierEntity.gekwalificeerdCategories && i === leverancierEntity.gekwalificeerdCategories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/leverancier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leverancier/${leverancierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeverancierDetail;
