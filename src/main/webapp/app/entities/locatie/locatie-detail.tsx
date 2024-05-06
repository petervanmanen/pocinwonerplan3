import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locatie.reducer';

export const LocatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locatieEntity = useAppSelector(state => state.locatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locatieDetailsHeading">Locatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locatieEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{locatieEntity.adres}</dd>
          <dt>Heeftlocatie Put</dt>
          <dd>
            {locatieEntity.heeftlocatiePuts
              ? locatieEntity.heeftlocatiePuts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.heeftlocatiePuts && i === locatieEntity.heeftlocatiePuts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Wordtbegrensddoor Project</dt>
          <dd>
            {locatieEntity.wordtbegrensddoorProjects
              ? locatieEntity.wordtbegrensddoorProjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.wordtbegrensddoorProjects && i === locatieEntity.wordtbegrensddoorProjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Verzoek</dt>
          <dd>
            {locatieEntity.betreftVerzoeks
              ? locatieEntity.betreftVerzoeks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.betreftVerzoeks && i === locatieEntity.betreftVerzoeks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Werkingsgebied Regeltekst</dt>
          <dd>
            {locatieEntity.werkingsgebiedRegelteksts
              ? locatieEntity.werkingsgebiedRegelteksts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.werkingsgebiedRegelteksts && i === locatieEntity.werkingsgebiedRegelteksts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Isverbondenmet Activiteit</dt>
          <dd>
            {locatieEntity.isverbondenmetActiviteits
              ? locatieEntity.isverbondenmetActiviteits.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.isverbondenmetActiviteits && i === locatieEntity.isverbondenmetActiviteits.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Verwijstnaar Gebiedsaanwijzing</dt>
          <dd>
            {locatieEntity.verwijstnaarGebiedsaanwijzings
              ? locatieEntity.verwijstnaarGebiedsaanwijzings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.verwijstnaarGebiedsaanwijzings && i === locatieEntity.verwijstnaarGebiedsaanwijzings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Geldtvoor Normwaarde</dt>
          <dd>
            {locatieEntity.geldtvoorNormwaardes
              ? locatieEntity.geldtvoorNormwaardes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locatieEntity.geldtvoorNormwaardes && i === locatieEntity.geldtvoorNormwaardes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/locatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locatie/${locatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocatieDetail;
