import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sportvereniging.reducer';

export const SportverenigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sportverenigingEntity = useAppSelector(state => state.sportvereniging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportverenigingDetailsHeading">Sportvereniging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sportverenigingEntity.id}</dd>
          <dt>
            <span id="aantalnormteams">Aantalnormteams</span>
          </dt>
          <dd>{sportverenigingEntity.aantalnormteams}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{sportverenigingEntity.adres}</dd>
          <dt>
            <span id="binnensport">Binnensport</span>
          </dt>
          <dd>{sportverenigingEntity.binnensport ? 'true' : 'false'}</dd>
          <dt>
            <span id="buitensport">Buitensport</span>
          </dt>
          <dd>{sportverenigingEntity.buitensport ? 'true' : 'false'}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{sportverenigingEntity.email}</dd>
          <dt>
            <span id="ledenaantal">Ledenaantal</span>
          </dt>
          <dd>{sportverenigingEntity.ledenaantal}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{sportverenigingEntity.naam}</dd>
          <dt>
            <span id="typesport">Typesport</span>
          </dt>
          <dd>{sportverenigingEntity.typesport}</dd>
          <dt>Oefentuit Sport</dt>
          <dd>
            {sportverenigingEntity.oefentuitSports
              ? sportverenigingEntity.oefentuitSports.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportverenigingEntity.oefentuitSports && i === sportverenigingEntity.oefentuitSports.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Gebruikt Sportlocatie</dt>
          <dd>
            {sportverenigingEntity.gebruiktSportlocaties
              ? sportverenigingEntity.gebruiktSportlocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportverenigingEntity.gebruiktSportlocaties && i === sportverenigingEntity.gebruiktSportlocaties.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sportvereniging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sportvereniging/${sportverenigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SportverenigingDetail;
