import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './melding.reducer';

export const MeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const meldingEntity = useAppSelector(state => state.melding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="meldingDetailsHeading">Melding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{meldingEntity.id}</dd>
          <dt>
            <span id="vierentwintiguurs">Vierentwintiguurs</span>
          </dt>
          <dd>{meldingEntity.vierentwintiguurs}</dd>
          <dt>
            <span id="datumtijd">Datumtijd</span>
          </dt>
          <dd>{meldingEntity.datumtijd}</dd>
          <dt>
            <span id="illegaal">Illegaal</span>
          </dt>
          <dd>{meldingEntity.illegaal}</dd>
          <dt>
            <span id="meldingnummer">Meldingnummer</span>
          </dt>
          <dd>{meldingEntity.meldingnummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{meldingEntity.omschrijving}</dd>
          <dt>Hoofdcategorie Categorie</dt>
          <dd>{meldingEntity.hoofdcategorieCategorie ? meldingEntity.hoofdcategorieCategorie.id : ''}</dd>
          <dt>Subcategorie Categorie</dt>
          <dd>{meldingEntity.subcategorieCategorie ? meldingEntity.subcategorieCategorie.id : ''}</dd>
          <dt>Betreft Containertype</dt>
          <dd>{meldingEntity.betreftContainertype ? meldingEntity.betreftContainertype.id : ''}</dd>
          <dt>Betreft Fractie</dt>
          <dd>{meldingEntity.betreftFractie ? meldingEntity.betreftFractie.id : ''}</dd>
          <dt>Betreft Locatie</dt>
          <dd>{meldingEntity.betreftLocatie ? meldingEntity.betreftLocatie.id : ''}</dd>
          <dt>Melder Medewerker</dt>
          <dd>{meldingEntity.melderMedewerker ? meldingEntity.melderMedewerker.id : ''}</dd>
          <dt>Uitvoerder Leverancier</dt>
          <dd>{meldingEntity.uitvoerderLeverancier ? meldingEntity.uitvoerderLeverancier.id : ''}</dd>
          <dt>Uitvoerder Medewerker</dt>
          <dd>{meldingEntity.uitvoerderMedewerker ? meldingEntity.uitvoerderMedewerker.id : ''}</dd>
          <dt>Betreft Beheerobject</dt>
          <dd>
            {meldingEntity.betreftBeheerobjects
              ? meldingEntity.betreftBeheerobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {meldingEntity.betreftBeheerobjects && i === meldingEntity.betreftBeheerobjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Schouwronde</dt>
          <dd>{meldingEntity.heeftSchouwronde ? meldingEntity.heeftSchouwronde.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/melding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/melding/${meldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MeldingDetail;
