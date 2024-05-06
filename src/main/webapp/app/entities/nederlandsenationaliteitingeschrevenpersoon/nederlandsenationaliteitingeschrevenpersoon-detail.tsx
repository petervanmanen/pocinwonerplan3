import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nederlandsenationaliteitingeschrevenpersoon.reducer';

export const NederlandsenationaliteitingeschrevenpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nederlandsenationaliteitingeschrevenpersoonEntity = useAppSelector(
    state => state.nederlandsenationaliteitingeschrevenpersoon.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nederlandsenationaliteitingeschrevenpersoonDetailsHeading">Nederlandsenationaliteitingeschrevenpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nederlandsenationaliteitingeschrevenpersoonEntity.id}</dd>
          <dt>
            <span id="aanduidingbijzondernederlanderschap">Aanduidingbijzondernederlanderschap</span>
          </dt>
          <dd>{nederlandsenationaliteitingeschrevenpersoonEntity.aanduidingbijzondernederlanderschap}</dd>
          <dt>
            <span id="nationaliteit">Nationaliteit</span>
          </dt>
          <dd>{nederlandsenationaliteitingeschrevenpersoonEntity.nationaliteit}</dd>
          <dt>
            <span id="redenverkrijgingnederlandsenationaliteit">Redenverkrijgingnederlandsenationaliteit</span>
          </dt>
          <dd>{nederlandsenationaliteitingeschrevenpersoonEntity.redenverkrijgingnederlandsenationaliteit}</dd>
          <dt>
            <span id="redenverliesnederlandsenationaliteit">Redenverliesnederlandsenationaliteit</span>
          </dt>
          <dd>{nederlandsenationaliteitingeschrevenpersoonEntity.redenverliesnederlandsenationaliteit}</dd>
        </dl>
        <Button tag={Link} to="/nederlandsenationaliteitingeschrevenpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/nederlandsenationaliteitingeschrevenpersoon/${nederlandsenationaliteitingeschrevenpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NederlandsenationaliteitingeschrevenpersoonDetail;
