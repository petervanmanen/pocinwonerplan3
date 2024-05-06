import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gebiedsaanwijzing.reducer';

export const GebiedsaanwijzingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gebiedsaanwijzingEntity = useAppSelector(state => state.gebiedsaanwijzing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gebiedsaanwijzingDetailsHeading">Gebiedsaanwijzing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gebiedsaanwijzingEntity.id}</dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{gebiedsaanwijzingEntity.groep}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{gebiedsaanwijzingEntity.naam}</dd>
          <dt>
            <span id="nen3610id">Nen 3610 Id</span>
          </dt>
          <dd>{gebiedsaanwijzingEntity.nen3610id}</dd>
          <dt>Verwijstnaar Locatie</dt>
          <dd>
            {gebiedsaanwijzingEntity.verwijstnaarLocaties
              ? gebiedsaanwijzingEntity.verwijstnaarLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {gebiedsaanwijzingEntity.verwijstnaarLocaties && i === gebiedsaanwijzingEntity.verwijstnaarLocaties.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Beschrijftgebiedsaanwijzing Instructieregel</dt>
          <dd>
            {gebiedsaanwijzingEntity.beschrijftgebiedsaanwijzingInstructieregels
              ? gebiedsaanwijzingEntity.beschrijftgebiedsaanwijzingInstructieregels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {gebiedsaanwijzingEntity.beschrijftgebiedsaanwijzingInstructieregels &&
                    i === gebiedsaanwijzingEntity.beschrijftgebiedsaanwijzingInstructieregels.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/gebiedsaanwijzing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gebiedsaanwijzing/${gebiedsaanwijzingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GebiedsaanwijzingDetail;
