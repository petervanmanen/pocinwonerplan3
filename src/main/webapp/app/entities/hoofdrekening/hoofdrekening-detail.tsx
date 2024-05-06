import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hoofdrekening.reducer';

export const HoofdrekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hoofdrekeningEntity = useAppSelector(state => state.hoofdrekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hoofdrekeningDetailsHeading">Hoofdrekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{hoofdrekeningEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{hoofdrekeningEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{hoofdrekeningEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{hoofdrekeningEntity.omschrijving}</dd>
          <dt>
            <span id="piahoofcategorieomschrijving">Piahoofcategorieomschrijving</span>
          </dt>
          <dd>{hoofdrekeningEntity.piahoofcategorieomschrijving}</dd>
          <dt>
            <span id="piahoofdcategoriecode">Piahoofdcategoriecode</span>
          </dt>
          <dd>{hoofdrekeningEntity.piahoofdcategoriecode}</dd>
          <dt>
            <span id="subcode">Subcode</span>
          </dt>
          <dd>{hoofdrekeningEntity.subcode}</dd>
          <dt>
            <span id="subcodeomschrijving">Subcodeomschrijving</span>
          </dt>
          <dd>{hoofdrekeningEntity.subcodeomschrijving}</dd>
          <dt>Heeft Activa</dt>
          <dd>
            {hoofdrekeningEntity.heeftActivas
              ? hoofdrekeningEntity.heeftActivas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {hoofdrekeningEntity.heeftActivas && i === hoofdrekeningEntity.heeftActivas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>
            {hoofdrekeningEntity.heeftKostenplaats
              ? hoofdrekeningEntity.heeftKostenplaats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {hoofdrekeningEntity.heeftKostenplaats && i === hoofdrekeningEntity.heeftKostenplaats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Valtbinnen Hoofdrekening 2</dt>
          <dd>{hoofdrekeningEntity.valtbinnenHoofdrekening2 ? hoofdrekeningEntity.valtbinnenHoofdrekening2.id : ''}</dd>
          <dt>Wordtgeschrevenop Inkooporder</dt>
          <dd>
            {hoofdrekeningEntity.wordtgeschrevenopInkooporders
              ? hoofdrekeningEntity.wordtgeschrevenopInkooporders.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {hoofdrekeningEntity.wordtgeschrevenopInkooporders && i === hoofdrekeningEntity.wordtgeschrevenopInkooporders.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/hoofdrekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hoofdrekening/${hoofdrekeningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoofdrekeningDetail;
