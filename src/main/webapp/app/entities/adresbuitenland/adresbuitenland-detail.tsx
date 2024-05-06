import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adresbuitenland.reducer';

export const AdresbuitenlandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adresbuitenlandEntity = useAppSelector(state => state.adresbuitenland.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adresbuitenlandDetailsHeading">Adresbuitenland</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{adresbuitenlandEntity.id}</dd>
          <dt>
            <span id="adresregelbuitenland1">Adresregelbuitenland 1</span>
          </dt>
          <dd>{adresbuitenlandEntity.adresregelbuitenland1}</dd>
          <dt>
            <span id="adresregelbuitenland2">Adresregelbuitenland 2</span>
          </dt>
          <dd>{adresbuitenlandEntity.adresregelbuitenland2}</dd>
          <dt>
            <span id="adresregelbuitenland3">Adresregelbuitenland 3</span>
          </dt>
          <dd>{adresbuitenlandEntity.adresregelbuitenland3}</dd>
          <dt>
            <span id="datumaanvangadresbuitenland">Datumaanvangadresbuitenland</span>
          </dt>
          <dd>
            {adresbuitenlandEntity.datumaanvangadresbuitenland ? (
              <TextFormat value={adresbuitenlandEntity.datumaanvangadresbuitenland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datuminschrijvinggemeente">Datuminschrijvinggemeente</span>
          </dt>
          <dd>
            {adresbuitenlandEntity.datuminschrijvinggemeente ? (
              <TextFormat value={adresbuitenlandEntity.datuminschrijvinggemeente} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumvestigingnederland">Datumvestigingnederland</span>
          </dt>
          <dd>
            {adresbuitenlandEntity.datumvestigingnederland ? (
              <TextFormat value={adresbuitenlandEntity.datumvestigingnederland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemeentevaninschrijving">Gemeentevaninschrijving</span>
          </dt>
          <dd>{adresbuitenlandEntity.gemeentevaninschrijving}</dd>
          <dt>
            <span id="landadresbuitenland">Landadresbuitenland</span>
          </dt>
          <dd>{adresbuitenlandEntity.landadresbuitenland}</dd>
          <dt>
            <span id="landwaarvandaaningeschreven">Landwaarvandaaningeschreven</span>
          </dt>
          <dd>{adresbuitenlandEntity.landwaarvandaaningeschreven}</dd>
          <dt>
            <span id="omschrijvingvandeaangifteadreshouding">Omschrijvingvandeaangifteadreshouding</span>
          </dt>
          <dd>{adresbuitenlandEntity.omschrijvingvandeaangifteadreshouding}</dd>
        </dl>
        <Button tag={Link} to="/adresbuitenland" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adresbuitenland/${adresbuitenlandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdresbuitenlandDetail;
