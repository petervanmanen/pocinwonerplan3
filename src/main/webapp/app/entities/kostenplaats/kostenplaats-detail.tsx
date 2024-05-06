import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kostenplaats.reducer';

export const KostenplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kostenplaatsEntity = useAppSelector(state => state.kostenplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kostenplaatsDetailsHeading">Kostenplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kostenplaatsEntity.id}</dd>
          <dt>
            <span id="btwcode">Btwcode</span>
          </dt>
          <dd>{kostenplaatsEntity.btwcode}</dd>
          <dt>
            <span id="btwomschrijving">Btwomschrijving</span>
          </dt>
          <dd>{kostenplaatsEntity.btwomschrijving}</dd>
          <dt>
            <span id="kostenplaatssoortcode">Kostenplaatssoortcode</span>
          </dt>
          <dd>{kostenplaatsEntity.kostenplaatssoortcode}</dd>
          <dt>
            <span id="kostenplaatssoortomschrijving">Kostenplaatssoortomschrijving</span>
          </dt>
          <dd>{kostenplaatsEntity.kostenplaatssoortomschrijving}</dd>
          <dt>
            <span id="kostenplaatstypecode">Kostenplaatstypecode</span>
          </dt>
          <dd>{kostenplaatsEntity.kostenplaatstypecode}</dd>
          <dt>
            <span id="kostenplaatstypeomschrijving">Kostenplaatstypeomschrijving</span>
          </dt>
          <dd>{kostenplaatsEntity.kostenplaatstypeomschrijving}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{kostenplaatsEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{kostenplaatsEntity.omschrijving}</dd>
          <dt>Heeft Inkooporder</dt>
          <dd>
            {kostenplaatsEntity.heeftInkooporders
              ? kostenplaatsEntity.heeftInkooporders.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {kostenplaatsEntity.heeftInkooporders && i === kostenplaatsEntity.heeftInkooporders.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Taakveld</dt>
          <dd>
            {kostenplaatsEntity.heeftTaakvelds
              ? kostenplaatsEntity.heeftTaakvelds.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {kostenplaatsEntity.heeftTaakvelds && i === kostenplaatsEntity.heeftTaakvelds.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Hoofdrekening</dt>
          <dd>
            {kostenplaatsEntity.heeftHoofdrekenings
              ? kostenplaatsEntity.heeftHoofdrekenings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {kostenplaatsEntity.heeftHoofdrekenings && i === kostenplaatsEntity.heeftHoofdrekenings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Project</dt>
          <dd>
            {kostenplaatsEntity.heeftProjects
              ? kostenplaatsEntity.heeftProjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {kostenplaatsEntity.heeftProjects && i === kostenplaatsEntity.heeftProjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/kostenplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kostenplaats/${kostenplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KostenplaatsDetail;
