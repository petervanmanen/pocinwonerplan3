import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './natuurlijkpersoon.reducer';

export const NatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const natuurlijkpersoonEntity = useAppSelector(state => state.natuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="natuurlijkpersoonDetailsHeading">Natuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="empty">Empty</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.empty}</dd>
          <dt>
            <span id="aanduidingbijzondernederlanderschappersoon">Aanduidingbijzondernederlanderschappersoon</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.aanduidingbijzondernederlanderschappersoon}</dd>
          <dt>
            <span id="aanduidingnaamgebruik">Aanduidingnaamgebruik</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.aanduidingnaamgebruik}</dd>
          <dt>
            <span id="aanhefaanschrijving">Aanhefaanschrijving</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.aanhefaanschrijving}</dd>
          <dt>
            <span id="academischetitel">Academischetitel</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.academischetitel}</dd>
          <dt>
            <span id="achternaam">Achternaam</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.achternaam}</dd>
          <dt>
            <span id="adellijketitelofpredikaat">Adellijketitelofpredikaat</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.adellijketitelofpredikaat}</dd>
          <dt>
            <span id="anummer">Anummer</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.anummer}</dd>
          <dt>
            <span id="burgerservicenummer">Burgerservicenummer</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.burgerservicenummer}</dd>
          <dt>
            <span id="datumgeboorte">Datumgeboorte</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.datumgeboorte}</dd>
          <dt>
            <span id="datumoverlijden">Datumoverlijden</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.datumoverlijden}</dd>
          <dt>
            <span id="geboorteland">Geboorteland</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.geboorteland}</dd>
          <dt>
            <span id="geboorteplaats">Geboorteplaats</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.geboorteplaats}</dd>
          <dt>
            <span id="geslachtsaanduiding">Geslachtsaanduiding</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.geslachtsaanduiding}</dd>
          <dt>
            <span id="geslachtsnaam">Geslachtsnaam</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.geslachtsnaam}</dd>
          <dt>
            <span id="geslachtsnaamaanschrijving">Geslachtsnaamaanschrijving</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.geslachtsnaamaanschrijving}</dd>
          <dt>
            <span id="handlichting">Handlichting</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.handlichting}</dd>
          <dt>
            <span id="indicatieafschermingpersoonsgegevens">Indicatieafschermingpersoonsgegevens</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.indicatieafschermingpersoonsgegevens ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatieoverleden">Indicatieoverleden</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.indicatieoverleden ? 'true' : 'false'}</dd>
          <dt>
            <span id="landoverlijden">Landoverlijden</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.landoverlijden}</dd>
          <dt>
            <span id="nationaliteit">Nationaliteit</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.nationaliteit}</dd>
          <dt>
            <span id="overlijdensplaats">Overlijdensplaats</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.overlijdensplaats}</dd>
          <dt>
            <span id="voorlettersaanschrijving">Voorlettersaanschrijving</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.voorlettersaanschrijving}</dd>
          <dt>
            <span id="voornamen">Voornamen</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.voornamen}</dd>
          <dt>
            <span id="voornamenaanschrijving">Voornamenaanschrijving</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.voornamenaanschrijving}</dd>
          <dt>
            <span id="voorvoegselgeslachtsnaam">Voorvoegselgeslachtsnaam</span>
          </dt>
          <dd>{natuurlijkpersoonEntity.voorvoegselgeslachtsnaam}</dd>
        </dl>
        <Button tag={Link} to="/natuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/natuurlijkpersoon/${natuurlijkpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NatuurlijkpersoonDetail;
