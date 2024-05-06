import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './school.reducer';

export const SchoolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const schoolEntity = useAppSelector(state => state.school.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schoolDetailsHeading">School</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{schoolEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{schoolEntity.naam}</dd>
          <dt>Kent Onderwijsloopbaan</dt>
          <dd>
            {schoolEntity.kentOnderwijsloopbaans
              ? schoolEntity.kentOnderwijsloopbaans.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {schoolEntity.kentOnderwijsloopbaans && i === schoolEntity.kentOnderwijsloopbaans.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Onderwijssoort</dt>
          <dd>
            {schoolEntity.heeftOnderwijssoorts
              ? schoolEntity.heeftOnderwijssoorts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {schoolEntity.heeftOnderwijssoorts && i === schoolEntity.heeftOnderwijssoorts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Gebruikt Sportlocatie</dt>
          <dd>
            {schoolEntity.gebruiktSportlocaties
              ? schoolEntity.gebruiktSportlocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {schoolEntity.gebruiktSportlocaties && i === schoolEntity.gebruiktSportlocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/school" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/school/${schoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchoolDetail;
