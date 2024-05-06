import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kadastraalperceel.reducer';

export const KadastraalperceelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kadastraalperceelEntity = useAppSelector(state => state.kadastraalperceel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kadastraalperceelDetailsHeading">Kadastraalperceel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kadastraalperceelEntity.id}</dd>
          <dt>
            <span id="aanduidingsoortgrootte">Aanduidingsoortgrootte</span>
          </dt>
          <dd>{kadastraalperceelEntity.aanduidingsoortgrootte}</dd>
          <dt>
            <span id="begrenzingperceel">Begrenzingperceel</span>
          </dt>
          <dd>{kadastraalperceelEntity.begrenzingperceel}</dd>
          <dt>
            <span id="grootteperceel">Grootteperceel</span>
          </dt>
          <dd>{kadastraalperceelEntity.grootteperceel}</dd>
          <dt>
            <span id="indicatiedeelperceel">Indicatiedeelperceel</span>
          </dt>
          <dd>{kadastraalperceelEntity.indicatiedeelperceel}</dd>
          <dt>
            <span id="omschrijvingdeelperceel">Omschrijvingdeelperceel</span>
          </dt>
          <dd>{kadastraalperceelEntity.omschrijvingdeelperceel}</dd>
          <dt>
            <span id="plaatscoordinatenperceel">Plaatscoordinatenperceel</span>
          </dt>
          <dd>{kadastraalperceelEntity.plaatscoordinatenperceel}</dd>
        </dl>
        <Button tag={Link} to="/kadastraalperceel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kadastraalperceel/${kadastraalperceelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KadastraalperceelDetail;
