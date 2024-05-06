import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './belijning.reducer';

export const BelijningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const belijningEntity = useAppSelector(state => state.belijning.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="belijningDetailsHeading">Belijning</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{belijningEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{belijningEntity.naam}</dd>
          <dt>Heeft Binnenlocatie</dt>
          <dd>
            {belijningEntity.heeftBinnenlocaties
              ? belijningEntity.heeftBinnenlocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {belijningEntity.heeftBinnenlocaties && i === belijningEntity.heeftBinnenlocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Veld</dt>
          <dd>
            {belijningEntity.heeftVelds
              ? belijningEntity.heeftVelds.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {belijningEntity.heeftVelds && i === belijningEntity.heeftVelds.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/belijning" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/belijning/${belijningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BelijningDetail;
